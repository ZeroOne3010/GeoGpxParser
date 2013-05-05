<?xml version="1.0" encoding="UTF-8"?>
<!--
    Description:
        Transforms a simple XML document (describing a table of data) into an HTML document.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"
                doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
                doctype-system="http://www.w3.org/TR/html4/loose.dtd"/>
    <xsl:template match="/table">
        <html>
            <head>
                <title>Caches</title>
                <style type="text/css">
                    body {
                        font-family: sans-serif;
                        font-size: 11pt;
                    }
                    table {
                        border-collapse: collapse;
                    }
                    th {
                        background-color: #cacaca;
                    }
                    td, th {
                        border: 1px solid #999;
                        padding: 2px 4px;
                    }
                    tr.odd td {
                        background-color: #e0e0e0;
                    }
                </style>
                <script type="text/javascript" src="jquery-1.9.1.min.js"></script>
                <script type="text/javascript" src="jquery.tablesorter.min.js"></script>
                <script type="text/javascript">
                $(document).ready(function() {
                    $("table").tablesorter({
                        widgets: ['zebra']
                    });
                });
                </script>
            </head>
            <body>
                <table id="{@identifier}">
                    <thead>
                        <xsl:apply-templates select="row[@header='true']" />
                    </thead>
                    <tbody>
                        <xsl:apply-templates select="row[@header='false']" />
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="row">
        <tr>
            <xsl:apply-templates select="cell" />
        </tr>
    </xsl:template>

    <xsl:template match="cell">
        <xsl:choose>
            <xsl:when test="@url">
                <td><a href="{@url}" target="_blank"><xsl:value-of select="." /></a></td>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="../@header='true'">
                        <th><xsl:value-of select="." /></th>
                    </xsl:when>
                    <xsl:otherwise>
                        <td><xsl:value-of select="." /></td>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
