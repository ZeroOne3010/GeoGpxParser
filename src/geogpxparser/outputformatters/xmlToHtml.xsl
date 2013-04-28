<?xml version="1.0" encoding="UTF-8"?>
<!--
    Description:
        Transforms a simple XML document (describing a table of data) into an HTML document.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="/table">
        <html>
            <head>
                <title>Caches</title>
                <style type="text/css">
                    table {
                        border-collapse: collapse;
                    }
                    td, th {
                        border: 1px solid #aaa;
                        padding: 2px 4px;
                    }
                </style>
            </head>
            <body>
                <table>
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
