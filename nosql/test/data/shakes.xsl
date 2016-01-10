<xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl">
<xsl:template match="/">
<xsl:for-each select="PLAY">
<HTML>
<xsl:element name="HEAD">
<title>
  <xsl:value-of select="MAINTITLE" /> 
  </title>
  </xsl:element>
<xsl:element name="body">
  <xsl:attribute name="background">
  <xsl:value-of select="bg" /> 
  </xsl:attribute>


  <xsl:apply-templates /> 


</xsl:element>

  </HTML>


  </xsl:for-each>



</xsl:template>

  <xsl:template match="textnode()">
  <xsl:value-of /> 
  </xsl:template>

<xsl:template match="PERSONAE | ACT">
<xsl:apply-templates/>
</xsl:template>




<xsl:template match="PERTITLE">
<font size="4"><p>
<xsl:apply-templates/>
</p></font>
</xsl:template>



<xsl:template match="PERSONA | GRPDESCR">
<font face="Arial" size="2"><i>
<xsl:apply-templates/>
</i></font><br/>
</xsl:template>

<xsl:template match="MAINTITLE">
<font face="Tahoma" size="3"><b>
<xsl:apply-templates/>
</b></font>
</xsl:template>

<xsl:template match="PGROUP | SCENE | SPEECH | EPILOGUE | PROLOGUE | INDUCT">
<p>
<xsl:apply-templates/>
</p>
</xsl:template>

<xsl:template match="SCNDESCR | PLAYSUBT | TITLE">
<p><font face="Tahoma" size="2"><b>
<xsl:apply-templates/>
</b></font></p>
</xsl:template>


<xsl:template match="SUBTITLE | SUBHEAD">
<p><font face="Tahoma" size="1"><b>
<xsl:apply-templates/>
</b></font></p>
</xsl:template>

<xsl:template match="STAGEDIR">
<p><font size="2" face="Arial">
<xsl:apply-templates/>
</font></p>
</xsl:template>

<xsl:template match="SPEAKER">
<font face="Tahoma" size="2"><b>
<xsl:apply-templates/>
</b></font>
</xsl:template>

<xsl:template match="LINE">
<font face="Tahoma" size="2"><br />
<xsl:apply-templates/>
</font>
</xsl:template>

  </xsl:stylesheet>