<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
        <xsl:output method="xml" omit-xml-declaration="yes" indent="no"/>
        <xsl:template match="/">
             <ListRecords>
                <xsl:for-each
				select="//results">		
					<record>				
				       <xsl:variable name="uid">
						<xsl:value-of
							select="./uid" />	
						</xsl:variable>

						<xsl:variable name="datestamp">
						<xsl:value-of
							select="./publishedAt" />	
						</xsl:variable>					
						<header>
							<identifier>
								<xsl:value-of select="$uid"/>
							</identifier>
							<datestamp>
								<xsl:value-of select="$datestamp"/>
							</datestamp>
						</header>
						<metadata>
							<oai_dc:dc xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd">
								<xsl:variable name="name">
								<xsl:value-of
									select="./name" />	
								</xsl:variable>
								<xsl:variable name="description">
								<xsl:value-of
									select="./description" />	
								</xsl:variable>
								
								<dc:title>
									<xsl:value-of select="$name"/>
								</dc:title>
								<dc:description>
									<xsl:value-of select="$description"/>
								</dc:description>
								
								<xsl:for-each select=".//categories">																				
									<xsl:variable name="subject">
									<xsl:value-of
										select="./name" />	
									</xsl:variable>
									<dc:subject>
										<xsl:value-of select="$subject"/>
									</dc:subject>	
								</xsl:for-each>	
								
								<xsl:variable name="thumbnail_url">
								<xsl:value-of
									select="./thumbnails/images[width='256']/url" />	
								</xsl:variable>
								
	
								<xsl:variable name="thumbnail_end">
									<xsl:value-of
										select="substring-after($thumbnail_url,'thumbnails/')" />
								</xsl:variable>
								
								<xsl:variable name="thumbnail">
									<xsl:value-of
										select="substring-before($thumbnail_end,'.jpeg')" />
								</xsl:variable>
								
								<dc:relation>
									<xsl:value-of select="$thumbnail"/>
								</dc:relation>
																				
																
							</oai_dc:dc>
						</metadata>						
					</record>				
				</xsl:for-each>
             </ListRecords> 
        </xsl:template>
</xsl:stylesheet>
