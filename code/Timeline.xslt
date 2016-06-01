<xsl:stylesheet version="3.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	extension-element-prefixes="saxon">
	<xsl:variable name="heightInPixels">
		<xsl:value-of select="1050" />
	</xsl:variable>

	<xsl:variable name="defaultStartY">
		<xsl:value-of select="30" />
	</xsl:variable>

	<xsl:variable name="peopleLastY" select="0" saxon:assignable="yes" />
	<xsl:variable name="maxPeopleLastY" select="0" saxon:assignable="yes" />

	<xsl:variable name="approximateYearAdjustment">
		<xsl:value-of select="1" />
	</xsl:variable>

	<xsl:template match="/">

<xsl:text>&#10;</xsl:text> <!-- newline character -->
<xsl:text>&#10;</xsl:text> <!-- newline character -->
<xsl:text>&#10;</xsl:text> <!-- newline character -->
<xsl:text>&#10;</xsl:text> <!-- newline character -->
<xsl:comment>


			Produced by Travis David Nelson

			gmail:travisdavidnelson

			PO Box 1884
			Idaho Springs, CO USA, 80452

			<xsl:value-of select="fn:current-dateTime()" />
			AD


</xsl:comment>
<xsl:text>&#10;</xsl:text> <!-- newline character -->
<xsl:text>&#10;</xsl:text> <!-- newline character -->
<xsl:text>&#10;</xsl:text> <!-- newline character -->
<xsl:text>&#10;</xsl:text> <!-- newline character -->
<xsl:text>&#10;</xsl:text> <!-- newline character -->

		<xsl:apply-templates select="timeline" />

	</xsl:template>

	<xsl:template match="timeline">

		<xsl:variable name="name">
	        <xsl:value-of select="name" />
		</xsl:variable>
		<xsl:variable name="stylesheet">
	        <xsl:value-of select="stylesheet" />
		</xsl:variable>
		<xsl:variable name="pixelsPerYear">
			<xsl:value-of select="fn:number(pixelsPerYear)" />
		</xsl:variable>
		<xsl:variable name="xBorder">
			<xsl:value-of select="fn:number(xBorder)" />
		</xsl:variable>
		<xsl:variable name="yBorder">
			<xsl:value-of select="fn:number(yBorder)" />
		</xsl:variable>
		<xsl:variable name="ySpacing">
			<xsl:value-of select="ySpacing" />
		</xsl:variable>
		<xsl:variable name="textXOffset">
			<xsl:value-of select="textXOffset" />
		</xsl:variable>
	
		<xsl:variable name="startYear">
	        <xsl:value-of select="range/startYear" />
		</xsl:variable>
		<xsl:variable name="endYear">
	        <xsl:value-of select="range/endYear" />
	    </xsl:variable>
		<xsl:variable name="widthInPixels">
			<xsl:call-template name="numberOfPixels">
				<xsl:with-param name="years" select="$endYear - $startYear + $xBorder" />
				<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
			</xsl:call-template>
		</xsl:variable>

		<saxon:assign name="peopleLastY" select="$yBorder + $defaultStartY" />
		<saxon:assign name="maxPeopleLastY" select="$peopleLastY" />
	

<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<title><xsl:value-of select="name" /></title>
		<link type="text/css" rel="stylesheet" href="./{$stylesheet}" />
	</head>
	<body>
<name value="{$name}"/>
<stylesheet value="{$stylesheet}"/>
<startYear value="{$startYear}"/>
<endYear value="{$endYear}"/>
<widthInPixels value="{$widthInPixels}"/>

		<xsl:text>&#10;</xsl:text> <!-- newline character -->
		<svg x="0px" y="0px" width="{$widthInPixels}px" height="{$heightInPixels}px"
			viewBox="0 0 {$widthInPixels} {$heightInPixels}" xml:space="preserve">
	   <defs>
	    <linearGradient id="fadeIn" x1="0" y1="0" x2="1" y2="0">
	      <stop offset="0" stop-color="white" stop-opacity="0" />
	      <stop offset="0.20" stop-color="white" stop-opacity="1.0" />
	    </linearGradient>
	    <mask id="fadeInMask" maskContentUnits="objectBoundingBox">
	      <rect width="1" height="1" fill="url(#fadeIn)" />
	    </mask>
	    <linearGradient id="fadeOut" x1="0" y1="0" x2="1" y2="0">
	      <stop offset="0.80" stop-color="white" stop-opacity="1.0" />
	      <stop offset="1.0" stop-color="white" stop-opacity="0" />
	    </linearGradient>
	    <mask id="fadeOutMask" maskContentUnits="objectBoundingBox">
	      <rect width="1" height="1" fill="url(#fadeOut)" />
	    </mask>
	    <linearGradient id="fadeInOut" x1="0" y1="0" x2="1" y2="0">
	      <stop offset="0" stop-color="white" stop-opacity="0" />
	      <stop offset="0.20" stop-color="white" stop-opacity="1.0" />
	      <stop offset="0.80" stop-color="white" stop-opacity="1.0" />
	      <stop offset="1.0" stop-color="white" stop-opacity="0" />
	    </linearGradient>
	    <mask id="fadeInOutMask" maskContentUnits="objectBoundingBox">
	      <rect width="1" height="1" fill="url(#fadeInOut)" />
	    </mask>
	  </defs>

	  <xsl:variable name="addCenturyTickLines">
	      <xsl:value-of select="addCenturyTickLines" />
	  </xsl:variable>
	  <addCenturyTickLines value="{$addCenturyTickLines}"/>
	  <xsl:variable name="defaultPersonBackgroundStyle">
	      <xsl:value-of select="defaultPersonBackgroundStyle" />
	  </xsl:variable>

	  <xsl:variable name="gridlineMod">
	      <xsl:value-of select="gridlineMod" />
	  </xsl:variable>
	  <xsl:variable name="majorTickMod">
	      <xsl:value-of select="majorTickMod" />
	  </xsl:variable>
	  <xsl:variable name="minorTickMod">
	      <xsl:value-of select="minorTickMod" />
	  </xsl:variable>

	  <xsl:variable name="minorHeight">
	      <xsl:value-of select="minorHeight" />
	  </xsl:variable>
	  <xsl:variable name="semimajorHeight">
	      <xsl:value-of select="semimajorHeight" />
	  </xsl:variable>
	  <xsl:variable name="majorHeight">
	      <xsl:value-of select="majorHeight" />
	  </xsl:variable>
	  <xsl:variable name="transformationalHeight">
	      <xsl:value-of select="transformationalHeight" />
	  </xsl:variable>
	  <xsl:variable name="minorTextYOffset">
	      <xsl:value-of select="minorTextYOffset" />
	  </xsl:variable>
	  <xsl:variable name="semimajorTextYOffset">
	      <xsl:value-of select="semimajorTextYOffset" />
	  </xsl:variable>
	  <xsl:variable name="majorTextYOffset">
	      <xsl:value-of select="majorTextYOffset" />
	  </xsl:variable>
	  <xsl:variable name="transformationalTextYOffset">
	      <xsl:value-of select="transformationalTextYOffset" />
	  </xsl:variable>

      <xsl:call-template name="displayGrid">
        <xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
        <xsl:with-param name="timelineStartYear" select="$startYear" />
        <xsl:with-param name="timelineEndYear" select="$endYear" />
        <xsl:with-param name="xBorder" select="$xBorder" />
        <xsl:with-param name="yBorder" select="$yBorder" />
		<xsl:with-param name="gridlineMod" select="$gridlineMod" />
		<xsl:with-param name="majorTickMod" select="$majorTickMod" />
		<xsl:with-param name="minorTickMod" select="$minorTickMod" />
      </xsl:call-template>

	  <xsl:apply-templates select="backgroundEvents">
		  <xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
    	  <xsl:with-param name="timelineStartYear" select="$startYear" />
		  <xsl:with-param name="timelineEndYear" select="$endYear" />
	      <xsl:with-param name="xBorder" select="$xBorder" />
	      <xsl:with-param name="yBorder" select="$yBorder" />
	  </xsl:apply-templates>
	  <xsl:apply-templates select="politicalDynastyGroups">
	      <xsl:with-param name="defaultPersonBackgroundStyle" select="$defaultPersonBackgroundStyle" />
		  <xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
    	  <xsl:with-param name="timelineStartYear" select="$startYear" />
		  <xsl:with-param name="timelineEndYear" select="$endYear" />
	      <xsl:with-param name="xBorder" select="$xBorder" />
	      <xsl:with-param name="yBorder" select="$yBorder" />
	      <xsl:with-param name="ySpacing" select="$ySpacing" />
	      <xsl:with-param name="textXOffset" select="$textXOffset" />
		  <xsl:with-param name="minorHeight" select="$minorHeight" />
		  <xsl:with-param name="semimajorHeight" select="$semimajorHeight" />
		  <xsl:with-param name="majorHeight" select="$majorHeight" />
		  <xsl:with-param name="transformationalHeight" select="$transformationalHeight" />
		  <xsl:with-param name="minorTextYOffset" select="$minorTextYOffset" />
		  <xsl:with-param name="semimajorTextYOffset" select="$semimajorTextYOffset" />
		  <xsl:with-param name="majorTextYOffset" select="$majorTextYOffset" />
		  <xsl:with-param name="transformationalTextYOffset" select="$transformationalTextYOffset" />
	  </xsl:apply-templates>

	  <xsl:apply-templates select="culturalDynastyGroups">
	      <xsl:with-param name="defaultPersonBackgroundStyle" select="$defaultPersonBackgroundStyle" />
		  <xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
    	  <xsl:with-param name="timelineStartYear" select="$startYear" />
		  <xsl:with-param name="timelineEndYear" select="$endYear" />
	      <xsl:with-param name="xBorder" select="$xBorder" />
	      <xsl:with-param name="yBorder" select="$yBorder" />
	      <xsl:with-param name="ySpacing" select="$ySpacing" />
	      <xsl:with-param name="textXOffset" select="$textXOffset" />
		  <xsl:with-param name="minorHeight" select="$minorHeight" />
		  <xsl:with-param name="semimajorHeight" select="$semimajorHeight" />
		  <xsl:with-param name="majorHeight" select="$majorHeight" />
		  <xsl:with-param name="transformationalHeight" select="$transformationalHeight" />
		  <xsl:with-param name="minorTextYOffset" select="$minorTextYOffset" />
		  <xsl:with-param name="semimajorTextYOffset" select="$semimajorTextYOffset" />
		  <xsl:with-param name="majorTextYOffset" select="$majorTextYOffset" />
		  <xsl:with-param name="transformationalTextYOffset" select="$transformationalTextYOffset" />
	  </xsl:apply-templates>
	  
<maxPeopleLastY value="{$maxPeopleLastY}"/>
    </svg>
	</body>
</html>
	</xsl:template>

	<xsl:template match="politicalDynastyGroups">
		<xsl:param name="defaultPersonBackgroundStyle" />
		<xsl:param name="pixelsPerYear" />
		<xsl:param name="timelineStartYear" />
		<xsl:param name="timelineEndYear" />
		<xsl:param name="xBorder" />
		<xsl:param name="yBorder" />
		<xsl:param name="ySpacing" />
		<xsl:param name="textXOffset" />
		<xsl:param name="minorHeight" />
		<xsl:param name="semimajorHeight" />
		<xsl:param name="majorHeight" />
		<xsl:param name="transformationalHeight" />
		<xsl:param name="minorTextYOffset" />
		<xsl:param name="semimajorTextYOffset" />
		<xsl:param name="majorTextYOffset" />
		<xsl:param name="transformationalTextYOffset" />

		<xsl:variable name="name">
			<xsl:value-of select="name" />
		</xsl:variable>

		<xsl:iterate select="dynasties">
			<xsl:param name="dynastyStartY" select="$yBorder + $defaultStartY" />

			<xsl:variable name="nextY" select="$dynastyStartY + $ySpacing" />
			<heresMyNextY value="{$nextY}" />

			<xsl:variable name="name">
				<xsl:value-of select="fn:upper-case(name)" />
			</xsl:variable>

			<xsl:variable name="startYear">
				<xsl:value-of select="fn:subsequence(people/titles/reign/startYear, 1, 1)" />
			</xsl:variable>
			<xsl:variable name="startX">
				<xsl:call-template name="yearToX">
					<xsl:with-param name="year" select="$startYear" />
					<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
					<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
					<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
					<xsl:with-param name="xBorder" select="$xBorder" />
				</xsl:call-template>
			</xsl:variable>

			<text x="{$startX}" y="{$nextY}" class="dynasty">
				<xsl:value-of select="$name" />
			</text>
			<xsl:text>&#10;</xsl:text> <!-- newline character -->

			<xsl:iterate select="people">
				<xsl:param name="peopleStartY" select="$nextY + $ySpacing" />

				<xsl:variable name="name">
					<xsl:value-of select="name" />
				</xsl:variable>
				<xsl:variable name="startYear">
					<xsl:value-of select="lifespan/startYear" />
				</xsl:variable>
				<xsl:variable name="endYear">
					<xsl:value-of select="lifespan/endYear" />
				</xsl:variable>
				<xsl:variable name="startYearApproximate">
					<xsl:value-of select="lifespan/startYearApproximate" />
				</xsl:variable>
				<xsl:variable name="endYearApproximate">
					<xsl:value-of select="lifespan/endYearApproximate" />
				</xsl:variable>
				<xsl:variable name="startYearApproxAdj">
					<xsl:call-template name="getApproximateYearAdjustment">
						<xsl:with-param name="approxFlag" select="$startYearApproximate" />
					</xsl:call-template>
				</xsl:variable>
				<xsl:variable name="endYearApproxAdj">
					<xsl:call-template name="getApproximateYearAdjustment">
						<xsl:with-param name="approxFlag" select="$endYearApproximate" />
					</xsl:call-template>
				</xsl:variable>
				<xsl:variable name="startX">
					<xsl:call-template name="yearToX">
						<xsl:with-param name="year" select="$startYear - $startYearApproxAdj" />
						<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
						<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
						<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
						<xsl:with-param name="xBorder" select="$xBorder" />
					</xsl:call-template>
				</xsl:variable>
				<xsl:variable name="endX">
					<xsl:call-template name="yearToX">
						<xsl:with-param name="year" select="$endYear + $endYearApproxAdj" />
						<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
						<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
						<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
						<xsl:with-param name="xBorder" select="$xBorder" />
					</xsl:call-template>
				</xsl:variable>
				<xsl:variable name="width" select="$endX - $startX" />

				<xsl:variable name="importance">
					<xsl:value-of select="importance" />
				</xsl:variable>
				<xsl:variable name="height">
					<xsl:call-template name="getImportanceValue">
						<xsl:with-param name="importance" select="$importance" />
						<xsl:with-param name="minorValue" select="$minorHeight" />
						<xsl:with-param name="semimajorValue" select="$semimajorHeight" />
						<xsl:with-param name="majorValue" select="$majorHeight" />
						<xsl:with-param name="transformationalValue" select="$transformationalHeight" />
					</xsl:call-template>
				</xsl:variable>
				<xsl:variable name="textYOffset">
					<xsl:call-template name="getImportanceValue">
						<xsl:with-param name="importance" select="$importance" />
						<xsl:with-param name="minorValue" select="$minorTextYOffset" />
						<xsl:with-param name="semimajorValue" select="$semimajorTextYOffset" />
						<xsl:with-param name="majorValue" select="$majorTextYOffset" />
						<xsl:with-param name="transformationalValue" select="$transformationalTextYOffset" />
					</xsl:call-template>
				</xsl:variable>

				<xsl:variable name="fadeMask">
					<xsl:call-template name="getFadeMask">
						<xsl:with-param name="startYearApproximate" select="$startYearApproximate" />
						<xsl:with-param name="endYearApproximate" select="$endYearApproximate" />
					</xsl:call-template>
				</xsl:variable>

				<xsl:variable name="backgroundStyle">
					<xsl:value-of select="backgroundStyle" />
				</xsl:variable>
				<xsl:variable name="classStyle">
					<xsl:call-template name="getClassStyle">
						<xsl:with-param name="backgroundStyle" select="$backgroundStyle" />
						<xsl:with-param name="defaultPersonBackgroundStyle" select="$defaultPersonBackgroundStyle" />
					</xsl:call-template>
				</xsl:variable>

				<xsl:variable name="displayStartYear">
					<xsl:call-template name="displayYear">
						<xsl:with-param name="year" select="$startYear" />
						<xsl:with-param name="approxFlag" select="$startYearApproximate" />
					</xsl:call-template>
				</xsl:variable>
				<xsl:variable name="displayEndYear">
					<xsl:call-template name="displayYear">
						<xsl:with-param name="year" select="$endYear" />
						<xsl:with-param name="approxFlag" select="$endYearApproximate" />
					</xsl:call-template>
				</xsl:variable>
				<xsl:variable name="annotation">
					<xsl:value-of select="annotation" />
				</xsl:variable>
				<xsl:variable name="fate">
					<xsl:value-of select="fate" />
				</xsl:variable>
				<xsl:variable name="personTooltip">
					<xsl:call-template name="getPersonTooltip">
						<xsl:with-param name="name" select="$name" />
						<xsl:with-param name="born" select="$displayStartYear" />
						<xsl:with-param name="died" select="$displayEndYear" />
						<xsl:with-param name="annotation" select="$annotation" />
						<xsl:with-param name="fate" select="$fate" />
					</xsl:call-template>
				</xsl:variable>

				<xsl:call-template name="getPersonSVG">
					<xsl:with-param name="name" select="$name" />
					<xsl:with-param name="startX" select="$startX" />
					<xsl:with-param name="startY" select="$peopleStartY" />
					<xsl:with-param name="textXOffset" select="$textXOffset" />
					<xsl:with-param name="textYOffset" select="$textYOffset" />
					<xsl:with-param name="width" select="$width" />
					<xsl:with-param name="height" select="$height" />
					<xsl:with-param name="fadeMask" select="$fadeMask" />
					<xsl:with-param name="classStyle" select="$classStyle" />
					<xsl:with-param name="importance" select="$importance" />
					<xsl:with-param name="startYearApproxAdj" select="$startYearApproxAdj" />
					<xsl:with-param name="personTooltip" select="$personTooltip" />
					<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
					<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
					<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
					<xsl:with-param name="xBorder" select="$xBorder" />
				</xsl:call-template>


				<xsl:variable name="peopleNextY" select="$peopleStartY + $height + $ySpacing" />

				<saxon:assign name="peopleLastY" select="$peopleNextY" />
				<xsl:choose>
					<xsl:when test="$peopleNextY &gt; $maxPeopleLastY"><saxon:assign name="maxPeopleLastY" select="$peopleNextY" /></xsl:when>
				</xsl:choose>
				

				<xsl:next-iteration>
					<xsl:with-param name="peopleStartY" select="$peopleNextY" />
				</xsl:next-iteration>

			</xsl:iterate>


			<xsl:next-iteration>
				<xsl:with-param name="dynastyStartY" select="$peopleLastY + (5 * $ySpacing)" />
			</xsl:next-iteration>

		</xsl:iterate>

	</xsl:template>


	<xsl:template match="culturalDynastyGroups">
		<xsl:param name="defaultPersonBackgroundStyle" />
		<xsl:param name="pixelsPerYear" />
		<xsl:param name="timelineStartYear" />
		<xsl:param name="timelineEndYear" />
		<xsl:param name="xBorder" />
		<xsl:param name="yBorder" />
		<xsl:param name="ySpacing" />
		<xsl:param name="textXOffset" />
		<xsl:param name="minorHeight" />
		<xsl:param name="semimajorHeight" />
		<xsl:param name="majorHeight" />
		<xsl:param name="transformationalHeight" />
		<xsl:param name="minorTextYOffset" />
		<xsl:param name="semimajorTextYOffset" />
		<xsl:param name="majorTextYOffset" />
		<xsl:param name="transformationalTextYOffset" />

		<xsl:variable name="name">
			<xsl:value-of select="name" />
		</xsl:variable>

		<xsl:iterate select="dynasties/people">
			<xsl:param name="peopleStartY" select="$maxPeopleLastY + (5 * $ySpacing)" />
			<xsl:param name="lastEndX" select="0" />

			<xsl:variable name="name">
				<xsl:value-of select="name" />
			</xsl:variable>
			<xsl:variable name="startYear">
				<xsl:value-of select="lifespan/startYear" />
			</xsl:variable>
			<xsl:variable name="endYear">
				<xsl:value-of select="lifespan/endYear" />
			</xsl:variable>
			<xsl:variable name="startYearApproximate">
				<xsl:value-of select="lifespan/startYearApproximate" />
			</xsl:variable>
			<xsl:variable name="endYearApproximate">
				<xsl:value-of select="lifespan/endYearApproximate" />
			</xsl:variable>
			<xsl:variable name="startYearApproxAdj">
				<xsl:call-template name="getApproximateYearAdjustment">
					<xsl:with-param name="approxFlag" select="$startYearApproximate" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:variable name="endYearApproxAdj">
				<xsl:call-template name="getApproximateYearAdjustment">
					<xsl:with-param name="approxFlag" select="$endYearApproximate" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:variable name="startX">
				<xsl:call-template name="yearToX">
					<xsl:with-param name="year" select="$startYear - $startYearApproxAdj" />
					<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
					<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
					<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
					<xsl:with-param name="xBorder" select="$xBorder" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:variable name="endX">
				<xsl:call-template name="yearToX">
					<xsl:with-param name="year" select="$endYear + $endYearApproxAdj" />
					<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
					<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
					<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
					<xsl:with-param name="xBorder" select="$xBorder" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:variable name="width" select="$endX - $startX" />

			<xsl:variable name="importance">
				<xsl:value-of select="importance" />
			</xsl:variable>
			<xsl:variable name="height">
				<xsl:call-template name="getImportanceValue">
					<xsl:with-param name="importance" select="$importance" />
					<xsl:with-param name="minorValue" select="$minorHeight" />
					<xsl:with-param name="semimajorValue" select="$semimajorHeight" />
					<xsl:with-param name="majorValue" select="$majorHeight" />
					<xsl:with-param name="transformationalValue" select="$transformationalHeight" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:variable name="textYOffset">
				<xsl:call-template name="getImportanceValue">
					<xsl:with-param name="importance" select="$importance" />
					<xsl:with-param name="minorValue" select="$minorTextYOffset" />
					<xsl:with-param name="semimajorValue" select="$semimajorTextYOffset" />
					<xsl:with-param name="majorValue" select="$majorTextYOffset" />
					<xsl:with-param name="transformationalValue" select="$transformationalTextYOffset" />
				</xsl:call-template>
			</xsl:variable>

			<xsl:variable name="fadeMask">
				<xsl:call-template name="getFadeMask">
					<xsl:with-param name="startYearApproximate" select="$startYearApproximate" />
					<xsl:with-param name="endYearApproximate" select="$endYearApproximate" />
				</xsl:call-template>
			</xsl:variable>

			<xsl:variable name="backgroundStyle">
				<xsl:value-of select="backgroundStyle" />
			</xsl:variable>
			<xsl:variable name="classStyle">
				<xsl:call-template name="getClassStyle">
					<xsl:with-param name="backgroundStyle" select="$backgroundStyle" />
					<xsl:with-param name="defaultPersonBackgroundStyle" select="$defaultPersonBackgroundStyle" />
				</xsl:call-template>
			</xsl:variable>

			<xsl:variable name="displayStartYear">
				<xsl:call-template name="displayYear">
					<xsl:with-param name="year" select="$startYear" />
					<xsl:with-param name="approxFlag" select="$startYearApproximate" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:variable name="displayEndYear">
				<xsl:call-template name="displayYear">
					<xsl:with-param name="year" select="$endYear" />
					<xsl:with-param name="approxFlag" select="$endYearApproximate" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:variable name="annotation">
				<xsl:value-of select="annotation" />
			</xsl:variable>
			<xsl:variable name="fate">
				<xsl:value-of select="fate" />
			</xsl:variable>
			<xsl:variable name="personTooltip">
				<xsl:call-template name="getPersonTooltip">
					<xsl:with-param name="name" select="$name" />
					<xsl:with-param name="born" select="$displayStartYear" />
					<xsl:with-param name="died" select="$displayEndYear" />
					<xsl:with-param name="annotation" select="$annotation" />
					<xsl:with-param name="fate" select="$fate" />
				</xsl:call-template>
			</xsl:variable>
			
			<xsl:variable name="thisPersonStartY">
				<xsl:call-template name="getPersonStartY">
					<xsl:with-param name="resetResult" select="$maxPeopleLastY + (5 * $ySpacing)" />
					<xsl:with-param name="iterateResult" select="$peopleStartY" />
					<xsl:with-param name="lastEndX" select="$lastEndX" />
					<xsl:with-param name="thisStartX" select="$startX" />
				</xsl:call-template>
			</xsl:variable>
<resetResult value="{$maxPeopleLastY + (5 * $ySpacing)}"/>			
<iterateResult value="{$peopleStartY}"/>			
<lastEndX value="{$lastEndX}"/>			
<thisStartX value="{$startX}"/>			

			<xsl:call-template name="getPersonSVG">
				<xsl:with-param name="name" select="$name" />
				<xsl:with-param name="startX" select="$startX" />
				<xsl:with-param name="startY" select="$thisPersonStartY" />
				<xsl:with-param name="textXOffset" select="$textXOffset" />
				<xsl:with-param name="textYOffset" select="$textYOffset" />
				<xsl:with-param name="width" select="$width" />
				<xsl:with-param name="height" select="$height" />
				<xsl:with-param name="fadeMask" select="$fadeMask" />
				<xsl:with-param name="classStyle" select="$classStyle" />
				<xsl:with-param name="importance" select="$importance" />
				<xsl:with-param name="startYearApproxAdj" select="$startYearApproxAdj" />
				<xsl:with-param name="personTooltip" select="$personTooltip" />
				<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
				<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
				<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
				<xsl:with-param name="xBorder" select="$xBorder" />
			</xsl:call-template>


			<xsl:variable name="peopleNextY" select="$thisPersonStartY + $height + $ySpacing" />

			<saxon:assign name="peopleLastY" select="$peopleNextY" />


			<xsl:next-iteration>
				<xsl:with-param name="peopleStartY" select="$peopleNextY" />
				<xsl:with-param name="lastEndX" select="$endX" />
			</xsl:next-iteration>


		</xsl:iterate>

	</xsl:template>


	<xsl:template name="getPersonSVG">
		<xsl:param name="name" />
		<xsl:param name="startX" />
		<xsl:param name="startY" />
		<xsl:param name="textXOffset" />
		<xsl:param name="textYOffset" />
		<xsl:param name="width" />
		<xsl:param name="height" />
		<xsl:param name="fadeMask" />
		<xsl:param name="classStyle" />
		<xsl:param name="importance" />
		<xsl:param name="startYearApproxAdj" />
		<xsl:param name="personTooltip" />
		<xsl:param name="pixelsPerYear" />
		<xsl:param name="timelineStartYear" />
		<xsl:param name="timelineEndYear" />
		<xsl:param name="xBorder" />
		
		<xsl:variable name="nameForLink" select="fn:replace($name, ' ', '_')" />
		<g>
			<a href="http://en.wikipedia.org/wiki/{$nameForLink}" target="_blank">
			<rect id="{$name}" x="{$startX}" y="{$startY}" width="{$width}"
				height="{$height}" class="{$classStyle}" mask="url(#{$fadeMask})" />

			<xsl:apply-templates select="titles">
				<xsl:with-param name="height" select="$height" />
				<xsl:with-param name="startY" select="$startY" />
				<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
				<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
				<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
				<xsl:with-param name="xBorder" select="$xBorder" />
			</xsl:apply-templates>

			<text x="{$startX + $textXOffset + $startYearApproxAdj}" y="{$startY + $textYOffset}" class="{$importance}">
				<xsl:value-of select="fn:upper-case($name)" />
			</text>
			<title><xsl:value-of select="$personTooltip" /></title>
			</a>
		</g>
		<xsl:text>&#10;</xsl:text> <!-- newline character -->
	</xsl:template>



	<xsl:template match="titles">
		<xsl:param name="height" />
		<xsl:param name="startY" />
		<xsl:param name="pixelsPerYear" />
		<xsl:param name="timelineStartYear" />
		<xsl:param name="timelineEndYear" />
		<xsl:param name="xBorder" />

		<xsl:variable name="name">
			<xsl:value-of select="name" />
		</xsl:variable>
		<xsl:variable name="startYear">
			<xsl:value-of select="reign/startYear" />
		</xsl:variable>
		<xsl:variable name="endYear">
			<xsl:value-of select="reign/endYear" />
		</xsl:variable>
		<xsl:variable name="startYearApproximate">
			<xsl:value-of select="reign/startYearApproximate" />
		</xsl:variable>
		<xsl:variable name="endYearApproximate">
			<xsl:value-of select="reign/endYearApproximate" />
		</xsl:variable>
		<xsl:variable name="startYearApproxAdj">
			<xsl:call-template name="getApproximateYearAdjustment">
				<xsl:with-param name="approxFlag" select="$startYearApproximate" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="endYearApproxAdj">
			<xsl:call-template name="getApproximateYearAdjustment">
				<xsl:with-param name="approxFlag" select="$endYearApproximate" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="startX">
			<xsl:call-template name="yearToX">
				<xsl:with-param name="year" select="$startYear - $startYearApproxAdj" />
				<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
				<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
				<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
				<xsl:with-param name="xBorder" select="$xBorder" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="width">
			<xsl:call-template name="numberOfPixels">
				<xsl:with-param name="years" select="($endYear + $endYearApproxAdj - ($startYear - $startYearApproxAdj))" />
				<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="fadeMask">
			<xsl:call-template name="getFadeMask">
				<xsl:with-param name="startYearApproximate" select="$startYearApproximate" />
				<xsl:with-param name="endYearApproximate" select="$endYearApproximate" />
			</xsl:call-template>
		</xsl:variable>

		<rect x="{$startX}" y="{$startY}" width="{$width}" height="{$height}" class="{$name}" mask="url(#{$fadeMask})" />

	</xsl:template>



	<xsl:template name="getFadeMask">
		<xsl:param name="startYearApproximate" />
		<xsl:param name="endYearApproximate" />
		<xsl:choose>
			<xsl:when test="$startYearApproximate = 'true' and $endYearApproximate = 'true'">fadeInOutMask</xsl:when>
			<xsl:when test="$startYearApproximate = 'true'">fadeInMask</xsl:when>
			<xsl:when test="$endYearApproximate = 'true'">fadeOutMask</xsl:when>
			<xsl:otherwise>none</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="getImportanceValue">
		<xsl:param name="importance" />
		<xsl:param name="minorValue" />
		<xsl:param name="semimajorValue" />
		<xsl:param name="majorValue" />
		<xsl:param name="transformationalValue" />
		<xsl:choose>
			<xsl:when test="$importance = 'minor'">
				<xsl:value-of select="$minorValue" />
			</xsl:when>
			<xsl:when test="$importance = 'semimajor'">
				<xsl:value-of select="$semimajorValue" />
			</xsl:when>
			<xsl:when test="$importance = 'major'">
				<xsl:value-of select="$majorValue" />
			</xsl:when>
			<xsl:when test="$importance = 'transformational'">
				<xsl:value-of select="$transformationalValue" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$minorValue" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="backgroundEvents">
		<xsl:param name="pixelsPerYear" />
		<xsl:param name="timelineStartYear" />
		<xsl:param name="timelineEndYear" />
		<xsl:param name="xBorder" />
		<xsl:param name="yBorder" />

		<xsl:variable name="startYear">
			<xsl:value-of select="dateRange/startYear" />
		</xsl:variable>
		<xsl:variable name="endYear">
			<xsl:value-of select="dateRange/endYear" />
		</xsl:variable>
		<xsl:variable name="height" select="1000 - $yBorder" />
		<xsl:variable name="name">
			<xsl:value-of select="name" />
		</xsl:variable>
		<xsl:variable name="style">
			<xsl:value-of select="style" />
		</xsl:variable>
		
		<xsl:variable name="startYearApproximate">
			<xsl:value-of select="dateRange/startYearApproximate" />
		</xsl:variable>
		<xsl:variable name="endYearApproximate">
			<xsl:value-of select="dateRange/endYearApproximate" />
		</xsl:variable>

		<xsl:variable name="startYearApproxAdj">
			<xsl:call-template name="getApproximateYearAdjustment">
				<xsl:with-param name="approxFlag" select="$startYearApproximate" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="endYearApproxAdj">
			<xsl:call-template name="getApproximateYearAdjustment">
				<xsl:with-param name="approxFlag" select="$endYearApproximate" />
			</xsl:call-template>
		</xsl:variable>
	
		<xsl:variable name="startX">
			<xsl:call-template name="yearToX">
				<xsl:with-param name="year" select="$startYear - $startYearApproxAdj" />
				<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
				<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
				<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
				<xsl:with-param name="xBorder" select="$xBorder" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="width">
			<xsl:call-template name="numberOfPixels">
				<xsl:with-param name="years" select="($endYear + $endYearApproxAdj - ($startYear - $startYearApproxAdj))" />
				<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
			</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="fadeMask">
			<xsl:call-template name="getFadeMask">
				<xsl:with-param name="startYearApproximate" select="$startYearApproximate" />
				<xsl:with-param name="endYearApproximate" select="$endYearApproximate" />
			</xsl:call-template>
		</xsl:variable>

		

		<g>
			<rect id="{$name}" x="{$startX}" y="{$yBorder}" width="{$width}" height="{$height}" class="{$style}"  mask="url(#{$fadeMask})" />
		</g>
		<xsl:text>&#10;</xsl:text> <!-- newline character -->

	</xsl:template>


	<xsl:template name="displayGrid">
		<xsl:param name="pixelsPerYear" />
		<xsl:param name="timelineStartYear" />
		<xsl:param name="timelineEndYear" />
		<xsl:param name="xBorder" />
		<xsl:param name="yBorder" />
		<xsl:param name="gridlineMod" />
		<xsl:param name="majorTickMod" />
		<xsl:param name="minorTickMod" />

		<xsl:variable name="timelineStartX">
			<xsl:call-template name="yearToX">
				<xsl:with-param name="year" select="$timelineStartYear" />
				<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
				<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
				<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
				<xsl:with-param name="xBorder" select="$xBorder" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="timelineEndX">
			<xsl:call-template name="yearToX">
				<xsl:with-param name="year" select="$timelineEndYear" />
				<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
				<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
				<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
				<xsl:with-param name="xBorder" select="$xBorder" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:call-template name="line">
			<xsl:with-param name="x1" select="$timelineStartX" />
			<xsl:with-param name="y1" select="$yBorder" />
			<xsl:with-param name="x2" select="$timelineEndX" />
			<xsl:with-param name="y2" select="$yBorder" />
		</xsl:call-template>
		<xsl:call-template name="line">
			<xsl:with-param name="x1" select="$timelineStartX" />
			<xsl:with-param name="y1" select="1000" />
			<xsl:with-param name="x2" select="$timelineEndX" />
			<xsl:with-param name="y2" select="1000" />
		</xsl:call-template>
		<xsl:call-template name="processYear">
			<xsl:with-param name="startYear" select="$timelineStartYear" />
			<xsl:with-param name="endYear" select="$timelineEndYear" />
			<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
			<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
			<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
			<xsl:with-param name="xBorder" select="$xBorder" />
			<xsl:with-param name="yBorder" select="$yBorder" />
			<xsl:with-param name="gridlineMod" select="$gridlineMod" />
			<xsl:with-param name="majorTickMod" select="$majorTickMod" />
			<xsl:with-param name="minorTickMod" select="$minorTickMod" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="processYear">
		<xsl:param name="startYear" />
		<xsl:param name="endYear" />
		<xsl:param name="pixelsPerYear" />
		<xsl:param name="timelineStartYear" />
		<xsl:param name="timelineEndYear" />
		<xsl:param name="xBorder" />
		<xsl:param name="yBorder" />
		<xsl:param name="gridlineMod" />
		<xsl:param name="majorTickMod" />
		<xsl:param name="minorTickMod" />

		<xsl:if test="not($startYear > $endYear)">
			<xsl:choose>
				<xsl:when test="$startYear = $endYear">
					<xsl:call-template name="gridline">
						<xsl:with-param name="year" select="$startYear" />
						<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
						<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
						<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
						<xsl:with-param name="xBorder" select="$xBorder" />
						<xsl:with-param name="yBorder" select="$yBorder" />
						<xsl:with-param name="gridlineMod" select="$gridlineMod" />
						<xsl:with-param name="majorTickMod" select="$majorTickMod" />
						<xsl:with-param name="minorTickMod" select="$minorTickMod" />
					</xsl:call-template>
				</xsl:when>
				<xsl:otherwise>
					<xsl:variable name="vMid" select="floor(($startYear + $endYear) div 2)" />
					<xsl:call-template name="processYear">
						<xsl:with-param name="startYear" select="$startYear" />
						<xsl:with-param name="endYear" select="$vMid" />
						<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
						<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
						<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
						<xsl:with-param name="xBorder" select="$xBorder" />
						<xsl:with-param name="yBorder" select="$yBorder" />
						<xsl:with-param name="gridlineMod" select="$gridlineMod" />
						<xsl:with-param name="majorTickMod" select="$majorTickMod" />
						<xsl:with-param name="minorTickMod" select="$minorTickMod" />
					</xsl:call-template>
					<xsl:call-template name="processYear">
						<xsl:with-param name="startYear" select="$vMid+1" />
						<xsl:with-param name="endYear" select="$endYear" />
						<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
						<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
						<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
						<xsl:with-param name="xBorder" select="$xBorder" />
						<xsl:with-param name="yBorder" select="$yBorder" />
						<xsl:with-param name="gridlineMod" select="$gridlineMod" />
						<xsl:with-param name="majorTickMod" select="$majorTickMod" />
						<xsl:with-param name="minorTickMod" select="$minorTickMod" />
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<xsl:template name="gridline">
		<xsl:param name="year" />
		<xsl:param name="pixelsPerYear" />
		<xsl:param name="timelineStartYear" />
		<xsl:param name="timelineEndYear" />
		<xsl:param name="xBorder" />
		<xsl:param name="yBorder" />
		<xsl:param name="gridlineMod" />
		<xsl:param name="majorTickMod" />
		<xsl:param name="minorTickMod" />

		<xsl:if test="($year mod $gridlineMod) = 0 or $year = 1">
			<xsl:variable name="xValue">
				<xsl:call-template name="yearToX">
					<xsl:with-param name="year" select="$year" />
					<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
					<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
					<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
					<xsl:with-param name="xBorder" select="$xBorder" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:call-template name="yearHeader">
				<xsl:with-param name="year" select="$year" />
				<xsl:with-param name="xValue" select="$xValue" />
				<xsl:with-param name="yBorder" select="$yBorder" />
			</xsl:call-template>
			<xsl:call-template name="line">
				<xsl:with-param name="x1" select="$xValue" />
				<xsl:with-param name="y1" select="$yBorder" />
				<xsl:with-param name="x2" select="$xValue" />
				<xsl:with-param name="y2" select="1000" />
			</xsl:call-template>
			<xsl:call-template name="yearFooter">
				<xsl:with-param name="year" select="$year" />
				<xsl:with-param name="xValue" select="$xValue" />
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="($year mod $minorTickMod) = 0">
			<xsl:variable name="xValue">
				<xsl:call-template name="yearToX">
					<xsl:with-param name="year" select="$year" />
					<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
					<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
					<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
					<xsl:with-param name="xBorder" select="$xBorder" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:call-template name="line">
				<xsl:with-param name="x1" select="$xValue" />
				<xsl:with-param name="y1" select="$yBorder" />
				<xsl:with-param name="x2" select="$xValue" />
				<xsl:with-param name="y2" select="fn:number($yBorder) + 10" />
			</xsl:call-template>
			<xsl:call-template name="line">
				<xsl:with-param name="x1" select="$xValue" />
				<xsl:with-param name="y1" select="1000 - 10" />
				<xsl:with-param name="x2" select="$xValue" />
				<xsl:with-param name="y2" select="1000" />
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="($year mod $majorTickMod) = 0">
			<xsl:variable name="xValue">
				<xsl:call-template name="yearToX">
					<xsl:with-param name="year" select="$year" />
					<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
					<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
					<xsl:with-param name="pixelsPerYear" select="$pixelsPerYear" />
					<xsl:with-param name="xBorder" select="$xBorder" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:call-template name="line">
				<xsl:with-param name="x1" select="$xValue" />
				<xsl:with-param name="y1" select="$yBorder" />
				<xsl:with-param name="x2" select="$xValue" />
				<xsl:with-param name="y2" select="fn:number($yBorder) + 15" />
			</xsl:call-template>
			<xsl:call-template name="line">
				<xsl:with-param name="x1" select="$xValue" />
				<xsl:with-param name="y1" select="1000 - 15" />
				<xsl:with-param name="x2" select="$xValue" />
				<xsl:with-param name="y2" select="1000" />
			</xsl:call-template>
		</xsl:if>

	</xsl:template>


	<xsl:template name="line">
		<xsl:param name="x1" />
		<xsl:param name="y1" />
		<xsl:param name="x2" />
		<xsl:param name="y2" />
		<xsl:param name="class" select="'timeline'" />

		<line x1="{$x1}" y1="{$y1}" x2="{$x2}" y2="{$y2}" class="{$class}" />
		<xsl:text>&#10;</xsl:text> <!-- newline character -->

	</xsl:template>

	<xsl:template name="yearToX">
		<xsl:param name="year" />
		<xsl:param name="timelineStartYear" />
		<xsl:param name="timelineEndYear" />
		<xsl:param name="pixelsPerYear" />
		<xsl:param name="xBorder" />
		<xsl:choose>
			<xsl:when test="$year > 0">
				<xsl:value-of select="(($pixelsPerYear)*($year)-($pixelsPerYear)-(($pixelsPerYear)*($timelineStartYear))) + fn:number($xBorder)" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="(($pixelsPerYear)*($year)-(($pixelsPerYear)*($timelineStartYear))) + $xBorder" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="numberOfPixels">
		<xsl:param name="years" />
		<xsl:param name="pixelsPerYear" />
		<xsl:choose>
			<xsl:when test="$years > 0"><xsl:value-of select="$pixelsPerYear * $years" /></xsl:when>
			<xsl:otherwise><xsl:value-of select="$pixelsPerYear" /></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="yearHeader">
		<xsl:param name="year" />
		<xsl:param name="xValue" />
		<xsl:param name="yBorder" />
		<xsl:choose>
			<xsl:when test="$year = 1">
				<text x="{$xValue - 20}" y="{$yBorder - 7}" class="year">
					AD <xsl:value-of select="$year" />
				</text>
			</xsl:when>
			<xsl:when test="$year > 0 and $year &lt; 1000">
				<text x="{$xValue - 15}" y="{$yBorder - 7}" class="year">
					<xsl:value-of select="$year" />
				</text>
			</xsl:when>
			<xsl:when test="$year >= 1000">
				<text x="{$xValue - 20}" y="{fn:number($yBorder) - 7}" class="year">
					<xsl:value-of select="$year" />
				</text>
			</xsl:when>
			<xsl:when test="$year &lt; 0">
				<text x="{$xValue - 20}" y="{$yBorder - 7}" class="year">
					<xsl:value-of select="fn:abs($year)" /> BC
				</text>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="yearFooter">
		<xsl:param name="year" />
		<xsl:param name="xValue" />
		<xsl:choose>
			<xsl:when test="$year = 1">
				<text x="{$xValue - 20}" y="1020" class="year">
					AD <xsl:value-of select="$year" />
				</text>
			</xsl:when>
			<xsl:when test="$year > 0 and $year &lt; 1000">
				<text x="{$xValue - 15}" y="1020" class="year">
					<xsl:value-of select="$year" />
				</text>
			</xsl:when>
			<xsl:when test="$year >= 1000">
				<text x="{$xValue - 20}" y="1020" class="year">
					<xsl:value-of select="$year" />
				</text>
			</xsl:when>
			<xsl:when test="$year &lt; 0">
				<text x="{$xValue - 20}" y="1020" class="year">
					<xsl:value-of select="fn:abs($year)" /> BC
				</text>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="displayYear">
		<xsl:param name="year" />
		<xsl:param name="approxFlag" />
		<xsl:choose>
			<xsl:when test="$year &lt; 1"><xsl:if test="$approxFlag = 'true'">c. </xsl:if><xsl:value-of select="fn:abs($year)" /> BC</xsl:when>
			<xsl:otherwise><xsl:value-of select="$year" /></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="getApproximateYearAdjustment">
		<xsl:param name="approxFlag" />
		<xsl:choose>
			<xsl:when test="$approxFlag = 'true'"><xsl:value-of select="$approximateYearAdjustment" /></xsl:when>
			<xsl:otherwise>0</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="getPersonTooltip">
		<xsl:param name="name" />
		<xsl:param name="born" />
		<xsl:param name="died" />
		<xsl:param name="annotation" select="''" />
		<xsl:param name="fate" select="''" />
		<xsl:value-of select="$name" /> (<xsl:value-of select="$born" /> - <xsl:value-of select="$died" />)
		<xsl:text>&#10;</xsl:text> <!-- newline character -->
		<xsl:value-of select="$annotation" />
		<xsl:text>&#10;</xsl:text> <!-- newline character -->
		<xsl:value-of select="$fate" />
	</xsl:template>

	<xsl:template name="getClassStyle">
		<xsl:param name="backgroundStyle" />
		<xsl:param name="defaultPersonBackgroundStyle" />
		<xsl:choose>
			<xsl:when test="$backgroundStyle = ''"><xsl:value-of select="$defaultPersonBackgroundStyle" /></xsl:when>
			<xsl:otherwise><xsl:value-of select="$backgroundStyle" /></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="getPersonStartY">
		<xsl:param name="resetResult" />
		<xsl:param name="iterateResult" />
		<xsl:param name="lastEndX" />
		<xsl:param name="thisStartX" />
		<xsl:choose>
			<xsl:when test="$thisStartX &lt; $lastEndX"><xsl:value-of select="$iterateResult" /></xsl:when>
			<xsl:otherwise><xsl:value-of select="$resetResult" /></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>