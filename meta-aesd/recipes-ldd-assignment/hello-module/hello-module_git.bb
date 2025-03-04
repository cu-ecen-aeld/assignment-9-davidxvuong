# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS += "misc-modules"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-davidxvuong.git;protocol=ssh;branch=main \
           file://S97hello-module \
          "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "b0217c71505cb5fe2bd4b814503e5f815694dbef"

S = "${WORKDIR}/git/misc-modules"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S97hello-module"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/S97hello-module ${D}${sysconfdir}/init.d/S97hello-module
	install -d ${D}/lib/modules/${KERNEL_VERSION}/extra
	install -m 0755 ${S}/hello.ko ${D}/lib/modules/${KERNEL_VERSION}/extra
}

FILES:${PN} += "${sysconfdir}/init.d"
FILES:${PN} += "${sysconfdir}/init.d/S97hello-module"
