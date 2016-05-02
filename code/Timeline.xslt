<xsl:stylesheet version="3.0" xmlns:saxon="http://saxon.sf.net/"
	xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	extension-element-prefixes="saxon">
	<xsl:variable name="widthInPixels">
		<xsl:value-of select="10000" />
	</xsl:variable>
	<xsl:variable name="heightInPixels">
		<xsl:value-of select="1000" />
	</xsl:variable>
	<xsl:variable name="pixelsPerYear">
		<xsl:value-of select="2" />
	</xsl:variable>

	<xsl:variable name="defaultStartY">
		<xsl:value-of select="40" />
	</xsl:variable>
	<xsl:variable name="yIncrement">
		<xsl:value-of select="5" />
	</xsl:variable>

	<xsl:variable name="peopleLastY" select="$defaultStartY"
		saxon:assignable="yes" />


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

		<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
			<head>
				<title>Timeline</title>
				<link type="text/css" rel="stylesheet" href="./RomeStyles.css" />
			</head>
			<body>
				<xsl:apply-templates select="timeline" />
			</body>
		</html>

	</xsl:template>

	<xsl:template match="timeline">

		<xsl:text>&#10;</xsl:text> <!-- newline character -->
		<xml-stylesheet type="text/css" href="./RomeStyles.css" />
		<xsl:text>&#10;</xsl:text> <!-- newline character -->
		<svg x="0px" y="0px" width="{$widthInPixels}px" height="{$heightInPixels}px"
			viewBox="0 0 {$widthInPixels} {$heightInPixels}" xml:space="preserve">
	   <defs>
	    <linearGradient id="fadeIn" x1="0" y1="0" x2="1"
			y2="0">
	      <stop offset="0" stop-color="white" stop-opacity="0" />
	      <stop offset="0.10" stop-color="white" stop-opacity="1.0" />
	    </linearGradient>
	    <mask id="fadeInMask" maskContentUnits="objectBoundingBox">
	      <rect width="1" height="1" fill="url(#fadeIn)" />
	    </mask>
	    <linearGradient id="fadeOut" x1="0" y1="0" x2="1"
			y2="0">
	      <stop offset="0.90" stop-color="white" stop-opacity="1.0" />
	      <stop offset="1.0" stop-color="white" stop-opacity="0" />
	    </linearGradient>
	    <mask id="fadeOutMask" maskContentUnits="objectBoundingBox">
	      <rect width="1" height="1" fill="url(#fadeOut)" />
	    </mask>
	    <linearGradient id="fadeInOut" x1="0" y1="0" x2="1"
			y2="0">
	      <stop offset="0" stop-color="white" stop-opacity="0" />
	      <stop offset="0.10" stop-color="white" stop-opacity="1.0" />
	      <stop offset="0.90" stop-color="white" stop-opacity="1.0" />
	      <stop offset="1.0" stop-color="white" stop-opacity="0" />
	    </linearGradient>
	    <mask id="fadeInOutMask" maskContentUnits="objectBoundingBox">
	      <rect width="1" height="1" fill="url(#fadeInOut)" />
	    </mask>
	  </defs>

	  <xsl:variable name="defaultPersonBackgroundStyle">
	      <xsl:value-of select="defaultPersonBackgroundStyle" />
	  </xsl:variable>

	  <xsl:variable name="startYear">
	      <xsl:value-of select="range/startYear" />
	  </xsl:variable>
	  <xsl:variable name="endYear">
	      <xsl:value-of select="range/endYear" />
	  </xsl:variable>

      <xsl:call-template name="displayGrid">
        <xsl:with-param name="timelineStartYear" select="$startYear" />
        <xsl:with-param name="timelineEndYear" select="$endYear" />
      </xsl:call-template>

	  <xsl:apply-templates select="backgroundEvents" />
	  <xsl:apply-templates select="politicalDynastyGroups">
	      <xsl:with-param name="defaultPersonBackgroundStyle"
			select="$defaultPersonBackgroundStyle" />
	  </xsl:apply-templates>
	  

    </svg>
	</xsl:template>

	<xsl:template match="politicalDynastyGroups">
		<xsl:param name="defaultPersonBackgroundStyle" />

		<xsl:variable name="name">
			<xsl:value-of select="name" />
		</xsl:variable>

		<xsl:iterate select="dynasties">
			<xsl:param name="dynastyStartY" select="$defaultStartY" />

			<xsl:variable name="nextY" select="$dynastyStartY + $yIncrement" />
			<heresMyNextY value="{$nextY}" />

			<xsl:variable name="name">
				<xsl:value-of select="fn:upper-case(name)" />
			</xsl:variable>

			<xsl:variable name="startYear">
				<xsl:value-of select="fn:subsequence(people/lifespan/startYear, 1, 1)" />
			</xsl:variable>
			<xsl:variable name="startX">
				<xsl:call-template name="yearToX">
					<xsl:with-param name="year" select="$startYear" />
				</xsl:call-template>
			</xsl:variable>

			<text x="{$startX}" y="{$nextY}" class="dynasty">
				<xsl:value-of select="$name" />
			</text>
			<xsl:text>&#10;</xsl:text> <!-- newline character -->

			<xsl:iterate select="people">
				<xsl:param name="peopleStartY" select="$nextY + $yIncrement" />

				<xsl:variable name="name">
					<xsl:value-of select="fn:upper-case(name)" />
				</xsl:variable>
				<xsl:variable name="startYear">
					<xsl:value-of select="lifespan/startYear" />
				</xsl:variable>
				<xsl:variable name="endYear">
					<xsl:value-of select="lifespan/endYear" />
				</xsl:variable>
				<xsl:variable name="startX">
					<xsl:call-template name="yearToX">
						<xsl:with-param name="year" select="$startYear" />
					</xsl:call-template>
				</xsl:variable>
				<xsl:variable name="endX">
					<xsl:call-template name="yearToX">
						<xsl:with-param name="year" select="$endYear" />
					</xsl:call-template>
				</xsl:variable>
				<xsl:variable name="width" select="$endX - $startX" />

				<xsl:variable name="importance">
					<xsl:value-of select="importance" />
				</xsl:variable>
				<xsl:variable name="height">
					<xsl:call-template name="getImportanceHeight">
						<xsl:with-param name="importance" select="$importance" />
					</xsl:call-template>
				</xsl:variable>
				<xsl:variable name="textYOffset">
					<xsl:call-template name="getTextYOffsetFromImportance">
						<xsl:with-param name="importance" select="$importance" />
					</xsl:call-template>
				</xsl:variable>

				<xsl:variable name="startYearApproximate">
					<xsl:value-of select="lifespan/startYearApproximate" />
				</xsl:variable>
				<xsl:variable name="endYearApproximate">
					<xsl:value-of select="lifespan/endYearApproximate" />
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
					<xsl:value-of
						select="if ($backgroundStyle = '') then $defaultPersonBackgroundStyle else $backgroundStyle" />
				</xsl:variable>

				<g>
					<rect id="{$name}" x="{$startX}" y="{$peopleStartY}" width="{$width}"
						height="{$height}" class="footprint" mask="url(#{$fadeMask})" />
					<rect id="{$name}" x="{$startX}" y="{$peopleStartY}" width="{$width}"
						height="{$height}" class="{$classStyle}" mask="url(#{$fadeMask})" />

					<xsl:apply-templates select="titles">
						<xsl:with-param name="height" select="$height" />
						<xsl:with-param name="startY" select="$peopleStartY" />
					</xsl:apply-templates>

					<text x="{$startX}" y="{$peopleStartY + $textYOffset}" class="{$importance}">
						<xsl:value-of select="$name" />
					</text>
					<title>
						<xsl:value-of select="$name" />
						(
						<xsl:value-of select="$startYear" />
						-
						<xsl:value-of select="$endYear" />
						)
					</title>
				</g>
				<xsl:text>&#10;</xsl:text> <!-- newline character -->


				<xsl:variable name="peopleNextY"
					select="$peopleStartY + $height + $yIncrement" />

				<heresMyPeopleNextY value="{$peopleNextY}" />

				<saxon:assign name="peopleLastY" select="$peopleNextY" />

				<xsl:next-iteration>
					<xsl:with-param name="peopleStartY" select="$peopleNextY" />
				</xsl:next-iteration>

			</xsl:iterate>


			<xsl:next-iteration>
				<xsl:with-param name="dynastyStartY"
					select="$peopleLastY + (3 * $yIncrement)" />
			</xsl:next-iteration>

		</xsl:iterate>

	</xsl:template>

	<xsl:template match="titles">
		<xsl:param name="height" />
		<xsl:param name="startY" />

		<xsl:variable name="name">
			<xsl:value-of select="name" />
		</xsl:variable>
		<xsl:variable name="startYear">
			<xsl:value-of select="reign/startYear" />
		</xsl:variable>
		<xsl:variable name="endYear">
			<xsl:value-of select="reign/endYear" />
		</xsl:variable>
		<xsl:variable name="startX">
			<xsl:call-template name="yearToX">
				<xsl:with-param name="year" select="$startYear" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="width">
			<xsl:call-template name="numberOfPixels">
				<xsl:with-param name="years" select="($endYear - $startYear)" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="startYearApproximate">
			<xsl:value-of select="reign/startYearApproximate" />
		</xsl:variable>
		<xsl:variable name="endYearApproximate">
			<xsl:value-of select="reign/endYearApproximate" />
		</xsl:variable>
		<xsl:variable name="fadeMask">
			<xsl:call-template name="getFadeMask">
				<xsl:with-param name="startYearApproximate" select="$startYearApproximate" />
				<xsl:with-param name="endYearApproximate" select="$endYearApproximate" />
			</xsl:call-template>
		</xsl:variable>

		<rect x="{$startX}" y="{$startY}" width="{$width}" height="{$height}"
			class="{$name}" />
		<xsl:text>&#10;</xsl:text> <!-- newline character -->

	</xsl:template>


	<xsl:template name="getFadeMask">
		<xsl:param name="startYearApproximate" />
		<xsl:param name="endYearApproximate" />
		<xsl:choose>
			<xsl:when
				test="$startYearApproximate = 'true' and $endYearApproximate = 'true'">
				fadeInOutMask
			</xsl:when>
			<xsl:when test="$startYearApproximate = 'true'">
				fadeInMask
			</xsl:when>
			<xsl:when test="$endYearApproximate = 'true'">
				fadeOutMask
			</xsl:when>
			<xsl:otherwise>
				none
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="getImportanceHeight">
		<xsl:param name="importance" />
		<xsl:choose>
			<xsl:when test="$importance = 'minor'">
				<xsl:value-of select="6" />
			</xsl:when>
			<xsl:when test="$importance = 'semimajor'">
				<xsl:value-of select="10" />
			</xsl:when>
			<xsl:when test="$importance = 'major'">
				<xsl:value-of select="15" />
			</xsl:when>
			<xsl:when test="$importance = 'transformational'">
				<xsl:value-of select="20" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="6" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="getTextYOffsetFromImportance">
		<xsl:param name="importance" />
		<xsl:choose>
			<xsl:when test="$importance = 'minor'">
				<xsl:value-of select="5" />
			</xsl:when>
			<xsl:when test="$importance = 'semimajor'">
				<xsl:value-of select="8" />
			</xsl:when>
			<xsl:when test="$importance = 'major'">
				<xsl:value-of select="10" />
			</xsl:when>
			<xsl:when test="$importance = 'transformational'">
				<xsl:value-of select="12" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="5" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>


	<xsl:template match="backgroundEvents">
		<xsl:variable name="startYear">
			<xsl:value-of select="dateRange/startYear" />
		</xsl:variable>
		<xsl:variable name="startX">
			<xsl:call-template name="yearToX">
				<xsl:with-param name="year" select="$startYear" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="endYear">
			<xsl:value-of select="dateRange/endYear" />
		</xsl:variable>
		<xsl:variable name="width">
			<xsl:call-template name="numberOfPixels">
				<xsl:with-param name="years" select="($endYear - $startYear)" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="height" select="1000 - 20" />
		<xsl:variable name="name">
			<xsl:value-of select="name" />
		</xsl:variable>
		<xsl:variable name="style">
			<xsl:value-of select="style" />
		</xsl:variable>

		<g>
			<rect id="{$name}" x="{$startX}" y="20" width="{$width}" height="{$height}"
				class="{$style}" />
		</g>
		<xsl:text>&#10;</xsl:text> <!-- newline character -->

	</xsl:template>


	<xsl:template name="displayGrid">
		<xsl:param name="timelineStartYear" />
		<xsl:param name="timelineEndYear" />

		<xsl:variable name="timelineStartX">
			<xsl:call-template name="yearToX">
				<xsl:with-param name="year" select="$timelineStartYear" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="timelineEndX">
			<xsl:call-template name="yearToX">
				<xsl:with-param name="year" select="$timelineEndYear" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:call-template name="line">
			<xsl:with-param name="x1" select="$timelineStartX" />
			<xsl:with-param name="y1" select="20" />
			<xsl:with-param name="x2" select="$timelineEndX" />
			<xsl:with-param name="y2" select="20" />
		</xsl:call-template>
		<xsl:call-template name="line">
			<xsl:with-param name="x1" select="$timelineStartX" />
			<xsl:with-param name="y1" select="1000" />
			<xsl:with-param name="x2" select="$timelineEndX" />
			<xsl:with-param name="y2" select="1000" />
		</xsl:call-template>
		<xsl:call-template name="processYear">
			<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
			<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="processYear">
		<xsl:param name="timelineStartYear" />
		<xsl:param name="timelineEndYear" />

		<xsl:if test="not($timelineStartYear > $timelineEndYear)">
			<xsl:choose>
				<xsl:when test="$timelineStartYear = $timelineEndYear">
					<xsl:call-template name="gridline">
						<xsl:with-param name="year" select="$timelineStartYear" />
					</xsl:call-template>
				</xsl:when>
				<xsl:otherwise>
					<xsl:variable name="vMid"
						select="floor(($timelineStartYear + $timelineEndYear) div 2)" />
					<xsl:call-template name="displayGrid">
						<xsl:with-param name="timelineStartYear" select="$timelineStartYear" />
						<xsl:with-param name="timelineEndYear" select="$vMid" />
					</xsl:call-template>
					<xsl:call-template name="processYear">
						<xsl:with-param name="timelineStartYear" select="$vMid+1" />
						<xsl:with-param name="timelineEndYear" select="$timelineEndYear" />
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<xsl:template name="gridline">
		<xsl:param name="year" />

		<xsl:if test="($year mod 100) = 0 or $year = 1">
			<xsl:variable name="xValue">
				<xsl:call-template name="yearToX">
					<xsl:with-param name="year" select="$year" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:call-template name="yearHeader">
				<xsl:with-param name="year" select="$year" />
				<xsl:with-param name="xValue" select="$xValue" />
			</xsl:call-template>
			<!-- <xsl:comment>year is <xsl:value-of select='$year'/></xsl:comment> 
				<xsl:comment>xValue is <xsl:value-of select='$xValue'/></xsl:comment> -->
			<xsl:call-template name="line">
				<xsl:with-param name="x1" select="$xValue" />
				<xsl:with-param name="y1" select="20" />
				<xsl:with-param name="x2" select="$xValue" />
				<xsl:with-param name="y2" select="1000" />
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="($year mod 10) = 0">
			<xsl:variable name="xValue">
				<xsl:call-template name="yearToX">
					<xsl:with-param name="year" select="$year" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:call-template name="line">
				<xsl:with-param name="x1" select="$xValue" />
				<xsl:with-param name="y1" select="20" />
				<xsl:with-param name="x2" select="$xValue" />
				<xsl:with-param name="y2" select="30" />
			</xsl:call-template>
			<xsl:call-template name="line">
				<xsl:with-param name="x1" select="$xValue" />
				<xsl:with-param name="y1" select="990" />
				<xsl:with-param name="x2" select="$xValue" />
				<xsl:with-param name="y2" select="1000" />
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="($year mod 50) = 0">
			<xsl:variable name="xValue">
				<xsl:call-template name="yearToX">
					<xsl:with-param name="year" select="$year" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:call-template name="line">
				<xsl:with-param name="x1" select="$xValue" />
				<xsl:with-param name="y1" select="20" />
				<xsl:with-param name="x2" select="$xValue" />
				<xsl:with-param name="y2" select="40" />
			</xsl:call-template>
			<xsl:call-template name="line">
				<xsl:with-param name="x1" select="$xValue" />
				<xsl:with-param name="y1" select="980" />
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
		<xsl:param name="timelineStartYear" select="-800" />
		<xsl:param name="timelineEndYear" select="1500" />
		<xsl:choose>
			<xsl:when test="$year > 0">
				<xsl:value-of
					select="(($pixelsPerYear)*($year)-($pixelsPerYear)-(($pixelsPerYear)*($timelineStartYear))) + 20" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of
					select="(($pixelsPerYear)*($year)-(($pixelsPerYear)*($timelineStartYear))) + 20" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="numberOfPixels">
		<xsl:param name="years" />
		<xsl:value-of select="fn:max(($pixelsPerYear * $years, $pixelsPerYear))" />
	</xsl:template>

	<xsl:template name="yearHeader">
		<xsl:param name="year" />
		<xsl:param name="xValue" />
		<xsl:choose>
			<xsl:when test="$year = 1">
				<text x="{$xValue}" y="13" class="year">
					AD
					<xsl:value-of select="$year" />
				</text>
			</xsl:when>
			<xsl:when test="$year > 0">
				<text x="{$xValue}" y="13" class="year">
					<xsl:value-of select="$year" />
				</text>
			</xsl:when>
			<xsl:when test="$year &lt; 0">
				<text x="{$xValue}" y="13" class="year">
					<xsl:value-of select="fn:abs($year)" />
					BC
				</text>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>