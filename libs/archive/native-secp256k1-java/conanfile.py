import os
from conans import ConanFile, CMake, tools


def get_version():
    with open(os.path.join(os.path.dirname(__file__), 'version'), 'r') as f:
        content = f.read()
        try:
            content = content.decode()
        except AttributeError:
            pass
        return content.strip()


class Secp256k1JavaConan(ConanFile):
    name = "secp256k1_java"
    version = get_version()
    license = "MIT"
    author = "Eduard Maximovich edward.vstock@gmail.com"
    url = "https://github.com/edwardstock/native-secp256k1-java"
    description = "Optimized C library for EC operations on curve secp256k1 with JNI-Bindings"
    topics = ("secp256k1", "secp256k1-jni", "secp256k1-java")
    settings = "os", "compiler", "build_type", "arch"
    options = {
        "shared": [True, False],
        "disableJNI": [True, False],
    }
    default_options = {
        "shared": False,
        "disableJNI": False,
    }
    exports = "version"
    exports_sources = (
        "modules/*",
        "include/*",
        "cfg/*",
        "tests/*",
        "src/*",
        "libs/*",
        "CMakeLists.txt",
        "conanfile.py",
        "LICENSE",
        "README.md",
    )
    generators = "cmake"
    default_user = "edwardstock"
    default_channel = "latest"

    def source(self):
        if "CONAN_LOCAL" not in os.environ:
            self.run("rm -rf *")
            self.run("git clone --recursive https://github.com/edwardstock/native-secp256k1-java.git .")

    def configure(self):
        if self.settings.compiler == "Visual Studio":
            del self.settings.compiler.runtime

    def build(self):
        cmake = CMake(self)
        opts = {
            'CMAKE_BUILD_TYPE': 'Release',
            'DISABLE_JNI': 'Off',
            'ENABLE_SHARED': 'Off'
        }

        if self.options.shared:
            opts['ENABLE_SHARED'] = 'On'

        if self.options.disableJNI:
            opts['DISABLE_JNI'] = 'On'

        opts['CMAKE_BUILD_TYPE'] = self.settings.get_safe("build_type")

        cmake.configure(defs=opts)
        if self.settings.compiler == "Visual Studio":
            cmake.build(args=['--config', self.settings.get_safe("build_type")])
        else:
            cmake.build()

    def package(self):
        self.copy("*", dst="include", src="include", keep_path=True)

        dir_types = ['bin', 'lib', 'Debug', 'Release', 'RelWithDebInfo', 'MinSizeRel']
        file_types = ['lib', 'dll', 'dll.a', 'a', 'so', 'exp', 'pdb', 'ilk', 'dylib']

        for dirname in dir_types:
            for ftype in file_types:
                self.copy("*." + ftype, src=dirname, dst="lib", keep_path=False)

    def package_info(self):
        self.cpp_info.includedirs = ['include']
        self.cpp_info.libs = ['secp256k1_core']

        if not self.options.disableJNI:
            self.cpp_info.libs.append('secp256k1_jni')
