[![Build Status](https://cloud.drone.io/api/badges/ZeroOne3010/GeoGpxParser/status.svg)](https://cloud.drone.io/ZeroOne3010/GeoGpxParser)

This program is great especially for analyzing your My Finds Pocket Query files.
You can also load in any other Pocket Query and, say, use this program to create
a web page of the caches, which you can then print out as a reference when you go
out caching. The web page version allows you to drag and drop caches into different
order and to hide the columns that you don't need.

Technically this program is used to parse one or more .gpx files of geocaches into 
plain old Java objects (POJO). The caches are then saved as an HTML file or a tab
delimited text file, one cache per row. In the latter format it is easy to load the
caches into a spreadsheet program for further inspection.

In addition to saving the caches as one file, a couple of other files are also
created: one with statistics about the owners of the caches, and another with
statistics about the countries of the caches. These files contains details on how 
many and what kind of caches have you found from different owners and from different
countries.

Download the GeoGPXParser.zip file if you just want to use the program and do
not care about the source code. :) The usage happens from the command line and
the simple instructions for that are inside the download package as README.txt.

Note: This project was previously hosted
at [Google Code](http://code.google.com/p/geocache-gpx-parser/)

This project uses the following external JavaScript libraries:

- [jQuery](http://jquery.com/)
- [jQuery UI](http://jqueryui.com/)
- [tablesorter](http://tablesorter.com/docs/)

They all are released under the [MIT-license](http://www.opensource.org/licenses/mit-license.php).
