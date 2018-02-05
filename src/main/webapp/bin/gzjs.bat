cd /d E:\Workstation\MyEclipse2013\platform\kp2.0\WebContent\bin

REM -------------- start package javascript --------------

type ..\js\dwz\dwz.core.js > dwzESC.js
type ..\js\dwz\dwz.util.date.js >> dwzESC.js
type ..\js\dwz\dwz.validate.method.js >> dwzESC.js
type ..\js\dwz\dwz.barDrag.js >> dwzESC.js
type ..\js\dwz\dwz.drag.js >> dwzESC.js
type ..\js\dwz\dwz.tree.js >> dwzESC.js
type ..\js\dwz\dwz.accordion.js >> dwzESC.js
type ..\js\dwz\dwz.ui.js >> dwzESC.js
type ..\js\dwz\dwz.theme.js >> dwzESC.js
type ..\js\dwz\dwz.switchEnv.js >> dwzESC.js

type ..\js\dwz\dwz.alertMsg.js >> dwzESC.js
type ..\js\dwz\dwz.contextmenu.js >> dwzESC.js
type ..\js\dwz\dwz.navTab.js >> dwzESC.js
type ..\js\dwz\dwz.tab.js >> dwzESC.js
type ..\js\dwz\dwz.resize.js >> dwzESC.js
type ..\js\dwz\dwz.dialog.js >> dwzESC.js
type ..\js\dwz\dwz.dialogDrag.js >> dwzESC.js
type ..\js\dwz\dwz.sortDrag.js >> dwzESC.js
type ..\js\dwz\dwz.cssTable.js >> dwzESC.js
type ..\js\dwz\dwz.stable.js >> dwzESC.js
type ..\js\dwz\dwz.taskBar.js >> dwzESC.js
type ..\js\dwz\dwz.ajax.js >> dwzESC.js
type ..\js\dwz\dwz.pagination.js >> dwzESC.js
type ..\js\dwz\dwz.database.js >> dwzESC.js
type ..\js\dwz\dwz.datepicker.js >> dwzESC.js
type ..\js\dwz\dwz.effects.js >> dwzESC.js
type ..\js\dwz\dwz.panel.js >> dwzESC.js
type ..\js\dwz\dwz.checkbox.js >> dwzESC.js
type ..\js\dwz\dwz.combox.js >> dwzESC.js
type ..\js\dwz\dwz.history.js >> dwzESC.js
type ..\js\dwz\dwz.print.js >> dwzESC.js

cscript ESC.wsf -l 1 -ow dwzESC1.js dwzESC.js
cscript ESC.wsf -l 2 -ow dwzESC2.js dwzESC1.js
cscript ESC.wsf -l 3 -ow dwzESC3.js dwzESC2.js

type dwzESC2.js > dwz.min.js
REM #gzip -f dwz.min.js
REM #copy dwz.min.js.gz dwz.min.gzjs /y
copy dwz.min.js ..\js\dwz /y

del dwzESC*.js
del dwz.min.js.gz