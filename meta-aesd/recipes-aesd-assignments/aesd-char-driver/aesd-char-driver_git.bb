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

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-davidxvuong;protocol=ssh;branch=main \
           file://S96aesdchar \
           file://module_load \
           file://module_unload \
          "


# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "374806a89d60b4a24677b3d732ade65985022782"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S96aesdchar"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
    install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/S96aesdchar ${D}${sysconfdir}/init.d/S96aesdchar
	install -d ${D}${base_sbindir}
	install -m 0755 ${WORKDIR}/module_load ${D}${base_sbindir}/module_load
	install -m 0755 ${WORKDIR}/module_unload ${D}${base_sbindir}/module_unload
	install -d ${D}/lib/modules/${KERNEL_VERSION}/extra
	install -m 0755 ${S}/aesdchar.ko ${D}/lib/modules/${KERNEL_VERSION}/extra
}

FILES:${PN} += "${base_sbindir}"
FILES:${PN} += "${base_sbindir}/module_load"
FILES:${PN} += "${base_sbindir}/module_unload"
FILES:${PN} += "${sysconfdir}/init.d"
FILES:${PN} += "${sysconfdir}/init.d/S96aesdchar"
