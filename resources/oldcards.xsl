<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xs="http://www.w3.org/2001/XMLSchema"
  xmlns:p="http://www.example.net/test"
  exclude-result-prefixes="xs p"
  version="2.0">
  <xsl:template match="/">
    <html>
      <body>
        <table style="border: 1px solid black;" cellspace="0">
          <tr>
            <td style="border-bottom: 1px solid black;">Id</td>
            <td style="border-bottom: 1px solid black;">Type</td>
            <td style="border-bottom: 1px solid black;">Theme</td>
            <td style="border-bottom: 1px solid black;">Year</td>
            <td style="border-bottom: 1px solid black;">Country</td>
            <td style="border-bottom: 1px solid black;">Authors</td>
            <td style="border-bottom: 1px solid black;">Valuable</td>
            <td style="border-bottom: 1px solid black;">Is sent</td>
          </tr>
          <xsl:for-each select="oldcards/oldcard">
            <tr>
              <td><xsl:value-of select="@id"/></td>
              <td><xsl:value-of select="type"/></td>
              <td><xsl:value-of select="theme"/></td>
              <td><xsl:value-of select="year"/></td>
              <td><xsl:value-of select="country"/></td>
              <td>
                <ul>
                  <xsl:for-each select="authors/author">
                  <li>
                    <xsl:value-of select="p:name"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="p:surname"/></li>
                  </xsl:for-each>
                </ul>
              </td>
              <td><xsl:value-of select="valuable"/></td>
              <td>
                <xsl:element name="input">
                  <xsl:attribute name="type">checkbox</xsl:attribute> 
                  <xsl:attribute name="name">isSent</xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="type/@isSent"/></xsl:attribute>
                  <xsl:if test="contains(type/@isSent,'yes')"> 
                    <xsl:attribute name="checked"></xsl:attribute>
                  </xsl:if>
                </xsl:element>
              </td>
            </tr>
          </xsl:for-each>        
        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>