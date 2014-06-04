[![Build Status](https://drone.io/bitbucket.org/ZeroOne3010/geogpxparser/status.png)](https://drone.io/bitbucket.org/ZeroOne3010/geogpxparser/latest)

This program is great especially for analyzing your My Finds Pocket Query files.
You can also use it to analyze other Pocket Queries in order to learn about the
caches in the given area.

Technically this program is used to parse one or more .gpx files of
geocaches into plain old Java objects (POJO). The caches are then saved as a tab
delimited text file, one cache per row. In this format it is easy to load the
caches into a spreadsheet program for further inspection. Alternatively you
can choose to save the caches as an HTML file instead for inspecting them with
your browser of choice.

In addition to saving the caches as one file, another file is also
saved: one with statistics about the owners of the caches. This file contains
details on how many caches have each of them hidden and what type of caches
are they.

Download the GeoGPXParser.zip file if you just want to use the program and do
not care about the source code. :) The usage happens from the command line and
the simple instructions for that are inside the download package as README.txt.

Note: This project was previously hosted
at [Google Code](http://code.google.com/p/geocache-gpx-parser/)

This project uses the following external JavaScript libraries:

- [jQuery](http://jquery.com/)
- [tablesorter](http://tablesorter.com/docs/)

Both are released under the [MIT-license](http://www.opensource.org/licenses/mit-license.php).