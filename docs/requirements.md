# Requirements

### The system shall:

#### ... accept a sequence of png images of size 1280x1024 as input.
The system will be designed to work with arbritrary size files assuming they are all the same.
There will be a special configuration file which specifies 1280x1024 as default.
This will enable the application to easily adjust to future requirements while fullfilling current needs.

#### ... report back to the user information regarding transition changes between images in the sequence.
The reports shall be made available in two files types.
1. Machine readable text files with the given syntax.
2. HTML files which enable an easy visual understanding of the changes.

The report shall also be available in visual form on via the application window.

#### ... quantify transition changes in the report.
Quantification revolves primarily around finding the corners of rectangles of change.
This can be done via a combination of basic mathematical morphology (to fill in holes in the image),
and a simple path-walking algorithm, which walks to the top-left and bottom-right corners of the
now filled in rectangle.

#### ... classify transition changes in the report.
Once the changes have been quantified, classifying them is a matter of looking at the location of the
changes and comparing them to the location of previous changes.


#### ... describe the rectangular boundary of each transition change that is found (either graphically or with text) as output.
Once quantification and classification are done, the report can be auto generated.


#### ... recognize window opening/closing.
#### ... recognize window moving/resizing.
#### ... recognize menus opening/closing.
#### ... recognize menu item selection.
#### ... recognize application area updates.
#### ... recognize window title bar clicks.
#### ... recognize window title updates.
#### ... recognize desktop icon changes.
#### ... recognize taskbar updates.
#### ... ignore updates to the clock (time and date).
