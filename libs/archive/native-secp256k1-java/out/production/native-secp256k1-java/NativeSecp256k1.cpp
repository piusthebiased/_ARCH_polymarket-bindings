#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include "secp256k1_recovery.h"
#include "NativeSecp256k1.h"
#include "secp256k1.h"
#include "secp256k1_ecdh.h"

JNIEXPORT jlong
JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1init_1context(
    JNIEnv *, jclass) {
    secp256k1_context *ctx =
        secp256k1_context_create(SECP256K1_CONTEXT_SIGN | SECP256K1_CONTEXT_VERIFY);

    return (uintptr_t) ctx;
}

JNIEXPORT jlong
JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1ctx_1clone(
    JNIEnv *, jclass, jlong ctx_l) {
    const secp256k1_context *ctx = (secp256k1_context *) (uintptr_t) ctx_l;

    jlong ctx_clone_l = (uintptr_t) secp256k1_context_clone(ctx);

    return ctx_clone_l;
}

JNIEXPORT jint
JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1context_1randomize(
    JNIEnv *env, jclass, jobject byteBufferObject, jlong ctx_l) {
    secp256k1_context *ctx = (secp256k1_context *) (uintptr_t) ctx_l;

    const uint8_t *seed = (uint8_t *) env->GetDirectBufferAddress(byteBufferObject);

    return secp256k1_context_randomize(ctx, seed);
}

JNIEXPORT void JNICALL
Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1destroy_1context(
    JNIEnv *, jclass, jlong ctx_l) {

    secp256k1_context *ctx = (secp256k1_context *) (uintptr_t) ctx_l;
    secp256k1_context_destroy(ctx);
}

JNIEXPORT jint
JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1ecdsa_1verify(
    JNIEnv *env,
    jclass,
    jobject byteBufferObject,
    jlong ctx_l,
    jint siglen,
    jint publen) {
    secp256k1_context *ctx = (secp256k1_context *) (uintptr_t) ctx_l;

    uint8_t *data = (uint8_t *) (env)->GetDirectBufferAddress(byteBufferObject);
    const uint8_t *sigdata = {(data + 32)};
    const uint8_t *pubdata = {(data + siglen + 32)};

    secp256k1_ecdsa_signature sig;
    secp256k1_pubkey pubkey;

    int ret = secp256k1_ecdsa_signature_parse_der(ctx, &sig, sigdata, (size_t) siglen);

    if (ret) {
        ret = secp256k1_ec_pubkey_parse(ctx, &pubkey, pubdata, (size_t) publen);

        if (ret) {
            ret = secp256k1_ecdsa_verify(ctx, &sig, data, &pubkey);
        }
    }

    return ret;
}

JNIEXPORT jobjectArray
JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1ecdsa_1sign(
    JNIEnv *env, jclass, jobject byteBufferObject, jlong ctx_l) {
    secp256k1_context *ctx = (secp256k1_context *) (uintptr_t) ctx_l;
    uint8_t *data = (uint8_t *) (env)->GetDirectBufferAddress(byteBufferObject);
    uint8_t *secKey = (uint8_t *) (data + 32);

    jobjectArray retArray;
    jbyteArray sigArray, intsByteArray;
    uint8_t intsarray[2];

    secp256k1_ecdsa_signature sig[72];

    int ret = secp256k1_ecdsa_sign(ctx, sig, data, secKey, NULL, NULL);

    uint8_t outputSer[72];
    size_t outputLen = 72;

    if (ret) {
        secp256k1_ecdsa_signature_serialize_der(ctx, outputSer, &outputLen, sig);
    }

    intsarray[0] = (uint8_t)outputLen;
    intsarray[1] = (uint8_t)ret;

    retArray = (env)->NewObjectArray(2, (env)->FindClass("[B"), (env)->NewByteArray(1));

    sigArray = (env)->NewByteArray(outputLen);
    (env)->SetByteArrayRegion(sigArray, 0, outputLen, (jbyte *) outputSer);
    (env)->SetObjectArrayElement(retArray, 0, sigArray);

    intsByteArray = (env)->NewByteArray(2);
    (env)->SetByteArrayRegion(intsByteArray, 0, 2, (jbyte *) intsarray);
    (env)->SetObjectArrayElement(retArray, 1, intsByteArray);

    return retArray;
}

JNIEXPORT
jobjectArray JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1ecdsa_1sign_1recoverable_1serialized(
    JNIEnv *env, jclass, jobject byteBufferObject, jlong ctx_l) {
    secp256k1_context *ctx = (secp256k1_context *) (uintptr_t) ctx_l;

    uint8_t *buffer = (uint8_t *) (env)->GetDirectBufferAddress(byteBufferObject);
    uint8_t data[32];
    memset(data, 0, 32);
    memmove(data, buffer, 32);
    uint8_t *secKey = buffer + 32;

    secp256k1_ecdsa_recoverable_signature sig;

    int ret = secp256k1_ecdsa_sign_recoverable(ctx, &sig, data, secKey, NULL, NULL);
    memset(secKey, 0, 32);
    memset(data, 0, 32);

    uint8_t outputSer[65];
    int recoveryId = 0;

    if (ret) {
        int serializeRet = secp256k1_ecdsa_recoverable_signature_serialize_compact(
            ctx,
            outputSer,
            &recoveryId,
            &sig);
        (void)serializeRet;
        outputSer[64] = ((uint8_t) recoveryId) + (uint8_t) 27;
    } else {
        return NULL;
    }

    jbyteArray rOut = env->NewByteArray(32);
    jbyteArray sOut = env->NewByteArray(32);
    jbyteArray vOut = env->NewByteArray(1);

    jbyte r[32], s[32], v[1];
    memcpy(r, outputSer + 0, 32);
    memcpy(s, outputSer + 32, 32);
    v[0] = outputSer[64];
    memset(outputSer, 0, 65);

    env->SetByteArrayRegion(rOut,
                            0, 32, r);
    env->SetByteArrayRegion(sOut,
                            0, 32, s);
    env->SetByteArrayRegion(vOut,
                            0, 1, v);

    jobjectArray retArray = (env)->NewObjectArray(3, (env)->FindClass("[B"), NULL);
    env->SetObjectArrayElement(retArray,
                               0, rOut);
    env->SetObjectArrayElement(retArray,
                               1, sOut);
    env->SetObjectArrayElement(retArray,
                               2, vOut);

    return retArray;
}

JNIEXPORT jint
JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1ec_1seckey_1verify(
    JNIEnv *env, jclass, jobject byteBufferObject, jlong ctx_l) {

    secp256k1_context *ctx = (secp256k1_context *) (uintptr_t) ctx_l;
    uint8_t *secKey = (uint8_t *) (env)->GetDirectBufferAddress(byteBufferObject);

    return
        secp256k1_ec_seckey_verify(ctx, secKey
        );
}

JNIEXPORT jobjectArray
JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1ec_1pubkey_1create(
    JNIEnv *env, jclass, jobject byteBufferObject, jlong ctx_l, jboolean compressed) {
    secp256k1_context *ctx = (secp256k1_context *) (uintptr_t) ctx_l;
    const uint8_t *secKey = (uint8_t *) (env)->GetDirectBufferAddress(byteBufferObject);

    secp256k1_pubkey pubkey;

    jobjectArray retArray;
    jbyteArray pubkeyArray, intsByteArray;
    uint8_t intsarray[2];

    int ret = secp256k1_ec_pubkey_create(ctx, &pubkey, secKey);

    uint8_t outputSer[65];
    memset(outputSer, 0, 65);
    size_t outputLen = 65;

    unsigned int compFlag =
        compressed == ((uint8_t) 1) ? SECP256K1_EC_COMPRESSED : SECP256K1_EC_UNCOMPRESSED;

    if (ret) {
        int ret2 = secp256k1_ec_pubkey_serialize(ctx, outputSer, &outputLen, &pubkey, compFlag);
        (void)ret2;
    }

    intsarray[0] = (uint8_t)outputLen;
    intsarray[1] = (uint8_t)ret;

    retArray = (env)->NewObjectArray(2, (env)->FindClass("[B"), (env)->NewByteArray(1));

    pubkeyArray = (env)->NewByteArray(outputLen);
    (env)->SetByteArrayRegion(pubkeyArray, 0, outputLen, (jbyte *) outputSer);
    (env)->SetObjectArrayElement(retArray, 0, pubkeyArray);

    intsByteArray = (env)->NewByteArray(2);
    (env)->SetByteArrayRegion(intsByteArray, 0, 2, (jbyte *) intsarray);
    (env)->SetObjectArrayElement(retArray, 1, intsByteArray);

    return retArray;
}

JNIEXPORT jobjectArray
JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1privkey_1tweak_1add(
    JNIEnv *env, jclass, jobject byteBufferObject, jlong ctx_l) {
    secp256k1_context *ctx = (secp256k1_context *) (uintptr_t) ctx_l;
    uint8_t *privkey = (uint8_t *) (env)->GetDirectBufferAddress(byteBufferObject);
    const uint8_t *tweak = (uint8_t *) (privkey + 32);

    jobjectArray retArray;
    jbyteArray privArray, intsByteArray;
    uint8_t intsarray[2];

    int privkeylen = 32;

    int ret = secp256k1_ec_privkey_tweak_add(ctx, privkey, tweak);

    intsarray[0] = privkeylen;
    intsarray[1] = ret;

    retArray = (env)->NewObjectArray(2, (env)->FindClass("[B"), (env)->NewByteArray(1));

    privArray = (env)->NewByteArray(privkeylen);
    (env)->SetByteArrayRegion(privArray, 0, privkeylen, (jbyte *) privkey);
    (env)->SetObjectArrayElement(retArray, 0, privArray);

    intsByteArray = (env)->NewByteArray(2);
    (env)->SetByteArrayRegion(intsByteArray, 0, 2, (jbyte *) intsarray);
    (env)->SetObjectArrayElement(retArray, 1, intsByteArray);

    return retArray;
}

JNIEXPORT jobjectArray
JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1privkey_1tweak_1mul(
    JNIEnv *env, jclass, jobject byteBufferObject, jlong ctx_l) {
    secp256k1_context *ctx = (secp256k1_context *) (uintptr_t) ctx_l;
    uint8_t *privkey = (uint8_t *) (env)->GetDirectBufferAddress(byteBufferObject);
    const uint8_t *tweak = (uint8_t *) (privkey + 32);

    jobjectArray retArray;
    jbyteArray privArray, intsByteArray;
    uint8_t intsarray[2];

    uint8_t privkeylen = 32;

    int ret = secp256k1_ec_privkey_tweak_mul(ctx, privkey, tweak);

    intsarray[0] = privkeylen;
    intsarray[1] = (uint8_t) ret;

    retArray = (env)->NewObjectArray(2, (env)->FindClass("[B"), (env)->NewByteArray(1));

    privArray = (env)->NewByteArray(privkeylen);
    (env)->SetByteArrayRegion(privArray, 0, privkeylen, (jbyte *) privkey);
    (env)->SetObjectArrayElement(retArray, 0, privArray);

    intsByteArray = (env)->NewByteArray(2);
    (env)->SetByteArrayRegion(intsByteArray, 0, 2, (jbyte *) intsarray);
    (env)->SetObjectArrayElement(retArray, 1, intsByteArray);

    return retArray;
}

JNIEXPORT jobjectArray
JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1pubkey_1tweak_1add(
    JNIEnv *env, jclass, jobject byteBufferObject, jlong ctx_l, jint publen) {
    secp256k1_context *ctx = (secp256k1_context *) (uintptr_t) ctx_l;

    uint8_t *pkey = (uint8_t *) ((env)->GetDirectBufferAddress(byteBufferObject));
    const uint8_t *tweak = (uint8_t *) (pkey + publen);

    jobjectArray retArray;
    jbyteArray pubArray, intsByteArray;
    uint8_t intsarray[2];
    uint8_t outputSer[65];
    size_t outputLen = 65;

    secp256k1_pubkey pubkey;
    int ret = secp256k1_ec_pubkey_parse(ctx, &pubkey, pkey, publen);

    if (ret) {
        ret = secp256k1_ec_pubkey_tweak_add(ctx, &pubkey, tweak);
    }

    if (ret) {
        int ret2 = secp256k1_ec_pubkey_serialize(ctx, outputSer, &outputLen, &pubkey, SECP256K1_EC_UNCOMPRESSED);
        (void)ret2;
    }

    intsarray[0] = (uint8_t) outputLen;
    intsarray[1] = (uint8_t) ret;

    retArray = (env)->NewObjectArray(2, (env)->FindClass("[B"), (env)->NewByteArray(1));

    pubArray = (env)->NewByteArray(outputLen);
    (env)->SetByteArrayRegion(pubArray, 0, outputLen, (jbyte *) outputSer);
    (env)->SetObjectArrayElement(retArray, 0, pubArray);

    intsByteArray = (env)->NewByteArray(2);
    (env)->SetByteArrayRegion(intsByteArray, 0, 2, (jbyte *) intsarray);
    (env)->SetObjectArrayElement(retArray, 1, intsByteArray);

    return retArray;
}

JNIEXPORT jobjectArray
JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1pubkey_1tweak_1mul(
    JNIEnv *env, jclass, jobject byteBufferObject, jlong ctx_l, jint publen) {
    secp256k1_context *ctx = (secp256k1_context *) (uintptr_t) ctx_l;
    uint8_t *pkey = static_cast<uint8_t *>((env)->GetDirectBufferAddress(byteBufferObject));
    const uint8_t *tweak = pkey + publen;

    jobjectArray retArray;
    jbyteArray pubArray, intsByteArray;
    uint8_t intsarray[2];
    uint8_t outputSer[65];
    size_t outputLen = 65;

    secp256k1_pubkey pubkey;
    int ret = secp256k1_ec_pubkey_parse(ctx, &pubkey, pkey, publen);

    if (ret) {
        ret = secp256k1_ec_pubkey_tweak_mul(ctx, &pubkey, tweak);
    }

    if (ret) {
        int ret2 = secp256k1_ec_pubkey_serialize(ctx, outputSer, &outputLen, &pubkey, SECP256K1_EC_UNCOMPRESSED);
        (void)ret2;
    }

    intsarray[0] = (uint8_t)outputLen;
    intsarray[1] = (uint8_t)ret;

    retArray = (env)->NewObjectArray(2, (env)->FindClass("[B"), (env)->NewByteArray(1));

    pubArray = (env)->NewByteArray(outputLen);
    (env)->SetByteArrayRegion(pubArray, 0, outputLen, (jbyte *) outputSer);
    (env)->SetObjectArrayElement(retArray, 0, pubArray);

    intsByteArray = (env)->NewByteArray(2);
    (env)->SetByteArrayRegion(intsByteArray, 0, 2, (jbyte *) intsarray);
    (env)->SetObjectArrayElement(retArray, 1, intsByteArray);

    return retArray;
}

JNIEXPORT jlong
JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1ecdsa_1pubkey_1combine(
    JNIEnv *, jclass, jobject, jlong, jint) {
    // dummy

    return 0;
}

JNIEXPORT jobjectArray
JNICALL Java_cryptography_elliptic_NativeSecp256k1_secp256k1_1ecdh(
    JNIEnv *env, jclass, jobject byteBufferObject, jlong ctx_l, jint publen) {
    auto *ctx = (secp256k1_context *) (uintptr_t) ctx_l;
    const uint8_t
        *secdata = static_cast<uint8_t *>((env)->GetDirectBufferAddress(byteBufferObject));
    const uint8_t *pubdata = secdata + 32;

    jobjectArray retArray;
    jbyteArray outArray, intsByteArray;
    uint8_t intsarray[1];
    secp256k1_pubkey pubkey;
    uint8_t nonce_res[32];
    size_t outputLen = 32;

    int ret = secp256k1_ec_pubkey_parse(ctx, &pubkey, pubdata, publen);

    if (ret) {
        ret = secp256k1_ecdh(
            ctx,
            nonce_res,
            &pubkey,
            secdata
        );
    }

    intsarray[0] = ret;

    retArray = (env)->NewObjectArray(2, (env)->FindClass("[B"), (env)->NewByteArray(1));

    outArray = (env)->NewByteArray(outputLen);
    (env)->SetByteArrayRegion(outArray, 0, 32, (jbyte *) nonce_res);
    (env)->SetObjectArrayElement(retArray, 0, outArray);

    intsByteArray = (env)->NewByteArray(1);
    (env)->SetByteArrayRegion(intsByteArray, 0, 1, (jbyte *) intsarray);
    (env)->SetObjectArrayElement(retArray, 1, intsByteArray);

    return retArray;
}
