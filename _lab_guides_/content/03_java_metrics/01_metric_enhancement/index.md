## Enhance the existing OpenTelemetry metric definitions

In this section, we will enhance the existing OpenTelemetry metric definitions by adding an additional attribute.


### 📌 Task #1: Enhance the existing metrics

**Your Task:** Enhance the existing metrics by adding an additional attribute

In the file `src/main/shop/FrontendServer.java`, the functions `reportPurchases` and `reportExpectedRevenue` have been updated to include an attribute with the key `user` and the value `System.getenv("GITHUB_USER")`, allowing metrics to be tied to individual attendee instances. Please update the `reportActualRevenue` function to include the same attribute.

<details>
  <summary>Solution: Expand to copy and paste the code</summary>

  ```java
private static void reportActualRevenue(Product product) {
	Attributes attributes = Attributes.builder()
    .put(AttributeKey.stringKey("product"), product.getName())
    .put(AttributeKey.stringKey("user"), System.getenv("GITHUB_USER"))
    .build();
		
	actualRevenueCounter.add(product.getPrice(), attributes);		
}
  ```
</details>

After making the changes above, restart your application by running `docker compose up -d --build` in the terminal.


#### 📌 Task #2: Upload `Shop Application Overview` dashboard

Copy the following JSON to a file and save it as `Shop Application Overview.json`. 

<details>
  <summary>Expand for dashboard JSON</summary>

```JSON
{
    "version": 15,
    "variables": [],
    "tiles": {
        "0": {
            "type": "markdown",
            "title": "",
            "content": "##\n## Business Analytics"
        },
        "3": {
            "type": "markdown",
            "title": "",
            "content": "##\n## Service Health"
        },
        "9": {
            "type": "data",
            "title": "",
            "query": "timeseries purchases=sum(shop.purchases.confirmed), by:{user}\n| fields Total = arraySum(purchases)",
            "davis": {
                "enabled": false,
                "davisVisualization": {
                    "isAvailable": true
                }
            },
            "visualization": "singleValue",
            "visualizationSettings": {
                "thresholds": [],
                "chartSettings": {
                    "xAxisScaling": "analyzedTimeframe",
                    "gapPolicy": "gap",
                    "circleChartSettings": {
                        "groupingThresholdType": "relative",
                        "groupingThresholdValue": 0,
                        "valueType": "relative"
                    },
                    "categoryOverrides": {},
                    "curve": "linear",
                    "pointsDisplay": "auto",
                    "categoricalBarChartSettings": {
                        "layout": "horizontal",
                        "categoryAxisTickLayout": "horizontal",
                        "scale": "absolute",
                        "groupMode": "stacked",
                        "colorPaletteMode": "multi-color",
                        "categoryAxisLabel": "user",
                        "valueAxisLabel": "interval",
                        "tooltipVariant": "single"
                    },
                    "colorPalette": "categorical",
                    "valueRepresentation": "absolute",
                    "truncationMode": "middle",
                    "hiddenLegendFields": []
                },
                "singleValue": {
                    "showLabel": true,
                    "label": "🛒 Total Purchases",
                    "prefixIcon": "",
                    "recordField": "Total",
                    "autoscale": true,
                    "alignment": "center",
                    "trend": {
                        "trendType": "auto",
                        "isVisible": true
                    },
                    "colorThresholdTarget": "background"
                },
                "table": {
                    "rowDensity": "condensed",
                    "enableSparklines": false,
                    "hiddenColumns": [],
                    "linewrapEnabled": false,
                    "lineWrapIds": [],
                    "monospacedFontEnabled": false,
                    "monospacedFontColumns": [],
                    "columnWidths": {},
                    "columnTypeOverrides": []
                },
                "honeycomb": {
                    "shape": "hexagon",
                    "legend": {
                        "hidden": false,
                        "position": "auto"
                    },
                    "displayedFields": [],
                    "dataMappings": {},
                    "truncationMode": "middle",
                    "colorMode": "color-palette",
                    "colorPalette": "blue"
                },
                "histogram": {
                    "legend": "auto",
                    "yAxis": {
                        "label": "Frequency",
                        "scale": "linear"
                    },
                    "colorPalette": "categorical",
                    "dataMappings": [
                        {
                            "valueAxis": "Total",
                            "rangeAxis": ""
                        }
                    ],
                    "variant": "single",
                    "truncationMode": "middle",
                    "displayedFields": []
                },
                "autoSelectVisualization": false,
                "unitsOverrides": [
                    {
                        "identifier": "Total",
                        "unitCategory": "unspecified",
                        "baseUnit": "none",
                        "displayUnit": null,
                        "decimals": 0,
                        "suffix": "",
                        "delimiter": false,
                        "added": 1732222476631
                    }
                ]
            },
            "querySettings": {
                "maxResultRecords": 1000,
                "defaultScanLimitGbytes": 500,
                "maxResultMegaBytes": 1,
                "defaultSamplingRatio": 10,
                "enableSampling": false
            }
        },
        "10": {
            "type": "markdown",
            "title": "",
            "content": "#\n# OpenTelemetry Hands-on Training - Orders Application"
        },
        "11": {
            "type": "markdown",
            "title": "",
            "content": "![](https://dt-cdn.net/images/opentelemetry-logo-250-c9f172fcee.png)"
        },
        "13": {
            "type": "data",
            "title": "Order Backend Response Time",
            "query": "timeseries avg(dt.service.request.response_time),filter: {matchesPhrase(entityAttr(dt.entity.service, \"entity.name\"), \"order-backend-*\")}",
            "davis": {
                "enabled": false,
                "davisVisualization": {
                    "isAvailable": true
                }
            },
            "visualization": "lineChart",
            "visualizationSettings": {
                "thresholds": [
                    {
                        "id": 1,
                        "field": "Total",
                        "title": "",
                        "isEnabled": true,
                        "rules": [
                            {
                                "id": 0,
                                "color": {
                                    "Default": "var(--dt-colors-charts-categorical-color-03-default, #2a7453)"
                                },
                                "comparator": "≥",
                                "label": "",
                                "value": 0
                            },
                            {
                                "id": 1,
                                "color": {
                                    "Default": "var(--dt-colors-charts-status-warning-default, #eca440)"
                                },
                                "comparator": "≥",
                                "label": ""
                            },
                            {
                                "id": 2,
                                "color": {
                                    "Default": "var(--dt-colors-charts-status-critical-default, #c4233b)"
                                },
                                "comparator": "≥",
                                "label": ""
                            }
                        ]
                    }
                ],
                "chartSettings": {
                    "xAxisScaling": "analyzedTimeframe",
                    "gapPolicy": "gap",
                    "circleChartSettings": {
                        "groupingThresholdType": "relative",
                        "groupingThresholdValue": 0,
                        "valueType": "relative"
                    },
                    "categoryOverrides": {},
                    "curve": "linear",
                    "pointsDisplay": "auto",
                    "categoricalBarChartSettings": {
                        "layout": "horizontal",
                        "categoryAxisTickLayout": "horizontal",
                        "scale": "absolute",
                        "groupMode": "stacked",
                        "colorPaletteMode": "multi-color",
                        "categoryAxisLabel": "dt.entity.service",
                        "valueAxisLabel": "interval",
                        "tooltipVariant": "single"
                    },
                    "colorPalette": "categorical",
                    "valueRepresentation": "absolute",
                    "truncationMode": "middle",
                    "fieldMapping": {
                        "timestamp": "timeframe",
                        "leftAxisValues": [
                            "avg(dt.service.request.response_time)"
                        ],
                        "leftAxisDimensions": []
                    },
                    "hiddenLegendFields": [
                        "user"
                    ],
                    "legend": {
                        "hidden": true
                    },
                    "leftYAxisSettings": {
                        "isLabelVisible": false
                    },
                    "xAxisIsLabelVisible": false
                },
                "singleValue": {
                    "showLabel": true,
                    "label": "Total Revenue",
                    "prefixIcon": "",
                    "recordField": "Total",
                    "autoscale": true,
                    "alignment": "center",
                    "trend": {
                        "trendType": "auto",
                        "isVisible": true
                    },
                    "colorThresholdTarget": "background"
                },
                "table": {
                    "rowDensity": "condensed",
                    "enableSparklines": false,
                    "hiddenColumns": [],
                    "linewrapEnabled": false,
                    "lineWrapIds": [],
                    "monospacedFontEnabled": false,
                    "monospacedFontColumns": [],
                    "columnWidths": {},
                    "columnTypeOverrides": []
                },
                "honeycomb": {
                    "shape": "hexagon",
                    "legend": {
                        "hidden": false,
                        "position": "auto"
                    },
                    "displayedFields": [],
                    "dataMappings": {},
                    "truncationMode": "middle",
                    "colorMode": "color-palette",
                    "colorPalette": "blue"
                },
                "histogram": {
                    "legend": "auto",
                    "yAxis": {
                        "label": "Frequency",
                        "scale": "linear"
                    },
                    "colorPalette": "categorical",
                    "dataMappings": [
                        {
                            "valueAxis": "interval",
                            "rangeAxis": ""
                        }
                    ],
                    "variant": "single",
                    "truncationMode": "middle",
                    "displayedFields": []
                },
                "autoSelectVisualization": false
            },
            "querySettings": {
                "maxResultRecords": 1000,
                "defaultScanLimitGbytes": 500,
                "maxResultMegaBytes": 1,
                "defaultSamplingRatio": 10,
                "enableSampling": false
            }
        },
        "14": {
            "type": "data",
            "title": "Top 10 Products by Sales",
            "query": "timeseries purchases=sum(shop.purchases.confirmed), by:{product}\n| fieldsAdd total=arraySum(purchases)\n| sort total desc\n| limit 10",
            "davis": {
                "enabled": false,
                "davisVisualization": {
                    "isAvailable": true
                }
            },
            "visualization": "categoricalBarChart",
            "visualizationSettings": {
                "thresholds": [],
                "chartSettings": {
                    "xAxisScaling": "analyzedTimeframe",
                    "gapPolicy": "gap",
                    "circleChartSettings": {
                        "groupingThresholdType": "relative",
                        "groupingThresholdValue": 0,
                        "valueType": "relative"
                    },
                    "categoryOverrides": {},
                    "curve": "linear",
                    "pointsDisplay": "auto",
                    "categoricalBarChartSettings": {
                        "layout": "horizontal",
                        "categoryAxisTickLayout": "horizontal",
                        "scale": "absolute",
                        "groupMode": "stacked",
                        "colorPaletteMode": "multi-color",
                        "categoryAxis": [
                            "product"
                        ],
                        "valueAxis": [
                            "total"
                        ],
                        "categoryAxisLabel": "product",
                        "valueAxisLabel": "total",
                        "tooltipVariant": "single"
                    },
                    "colorPalette": "categorical",
                    "valueRepresentation": "absolute",
                    "truncationMode": "middle",
                    "fieldMapping": {
                        "timestamp": "timeframe",
                        "leftAxisValues": [
                            "purchases"
                        ],
                        "leftAxisDimensions": [
                            "product",
                            "total"
                        ]
                    },
                    "hiddenLegendFields": [],
                    "legend": {
                        "hidden": true
                    }
                },
                "singleValue": {
                    "showLabel": true,
                    "label": "",
                    "prefixIcon": "",
                    "recordField": "user",
                    "autoscale": true,
                    "alignment": "center",
                    "trend": {
                        "trendType": "auto",
                        "isVisible": true
                    },
                    "colorThresholdTarget": "value"
                },
                "table": {
                    "rowDensity": "condensed",
                    "enableSparklines": false,
                    "hiddenColumns": [],
                    "linewrapEnabled": false,
                    "lineWrapIds": [],
                    "monospacedFontEnabled": false,
                    "monospacedFontColumns": [],
                    "columnWidths": {},
                    "columnTypeOverrides": []
                },
                "honeycomb": {
                    "shape": "hexagon",
                    "legend": {
                        "hidden": false,
                        "position": "auto"
                    },
                    "displayedFields": [
                        "product"
                    ],
                    "dataMappings": {
                        "value": "interval"
                    },
                    "truncationMode": "middle",
                    "colorMode": "color-palette",
                    "colorPalette": "blue"
                },
                "histogram": {
                    "legend": "auto",
                    "yAxis": {
                        "label": "Frequency",
                        "scale": "linear"
                    },
                    "colorPalette": "categorical",
                    "dataMappings": [
                        {
                            "valueAxis": "interval",
                            "rangeAxis": ""
                        },
                        {
                            "valueAxis": "total",
                            "rangeAxis": ""
                        }
                    ],
                    "variant": "single",
                    "truncationMode": "middle",
                    "displayedFields": [
                        "product"
                    ]
                },
                "autoSelectVisualization": false
            },
            "querySettings": {
                "maxResultRecords": 1000,
                "defaultScanLimitGbytes": 500,
                "maxResultMegaBytes": 1,
                "defaultSamplingRatio": 10,
                "enableSampling": false
            }
        },
        "15": {
            "type": "data",
            "title": "",
            "query": "timeseries {revenue=sum(shop.revenue.actual), purchases=sum(shop.purchases.confirmed)}, by:{user}\n| fields Average=arraySum(revenue)/arraySum(purchases)",
            "davis": {
                "enabled": false,
                "davisVisualization": {
                    "isAvailable": true
                }
            },
            "visualization": "singleValue",
            "visualizationSettings": {
                "thresholds": [],
                "chartSettings": {
                    "xAxisScaling": "analyzedTimeframe",
                    "gapPolicy": "gap",
                    "circleChartSettings": {
                        "groupingThresholdType": "relative",
                        "groupingThresholdValue": 0,
                        "valueType": "relative"
                    },
                    "categoryOverrides": {},
                    "curve": "linear",
                    "pointsDisplay": "auto",
                    "categoricalBarChartSettings": {
                        "layout": "horizontal",
                        "categoryAxisTickLayout": "horizontal",
                        "scale": "absolute",
                        "groupMode": "stacked",
                        "colorPaletteMode": "multi-color",
                        "categoryAxisLabel": "user",
                        "valueAxisLabel": "avgpurchase",
                        "tooltipVariant": "single"
                    },
                    "colorPalette": "categorical",
                    "valueRepresentation": "absolute",
                    "truncationMode": "middle",
                    "hiddenLegendFields": []
                },
                "singleValue": {
                    "showLabel": true,
                    "label": "💵 Average Purchase Amount",
                    "prefixIcon": "",
                    "recordField": "Average",
                    "autoscale": true,
                    "alignment": "center",
                    "trend": {
                        "trendType": "auto",
                        "isVisible": true
                    },
                    "colorThresholdTarget": "background"
                },
                "table": {
                    "rowDensity": "condensed",
                    "enableSparklines": false,
                    "hiddenColumns": [],
                    "linewrapEnabled": false,
                    "lineWrapIds": [],
                    "monospacedFontEnabled": false,
                    "monospacedFontColumns": [],
                    "columnWidths": {},
                    "columnTypeOverrides": []
                },
                "honeycomb": {
                    "shape": "hexagon",
                    "legend": {
                        "hidden": false,
                        "position": "auto"
                    },
                    "displayedFields": [],
                    "dataMappings": {},
                    "truncationMode": "middle",
                    "colorMode": "color-palette",
                    "colorPalette": "blue"
                },
                "histogram": {
                    "legend": "auto",
                    "yAxis": {
                        "label": "Frequency",
                        "scale": "linear"
                    },
                    "colorPalette": "categorical",
                    "dataMappings": [
                        {
                            "valueAxis": "Average",
                            "rangeAxis": ""
                        }
                    ],
                    "variant": "single",
                    "truncationMode": "middle",
                    "displayedFields": []
                },
                "autoSelectVisualization": false,
                "unitsOverrides": []
            },
            "querySettings": {
                "maxResultRecords": 1000,
                "defaultScanLimitGbytes": 500,
                "maxResultMegaBytes": 1,
                "defaultSamplingRatio": 10,
                "enableSampling": false
            }
        },
        "16": {
            "type": "data",
            "title": "",
            "query": "timeseries {expected=sum(shop.revenue.expected), actual=sum(shop.revenue.actual)}, by:{user}\n| fieldsAdd Lost=arraySum(expected)-arraySum(actual)",
            "davis": {
                "enabled": false,
                "davisVisualization": {
                    "isAvailable": true
                }
            },
            "visualization": "singleValue",
            "visualizationSettings": {
                "thresholds": [
                    {
                        "id": 1,
                        "field": "Lost",
                        "title": "",
                        "isEnabled": true,
                        "rules": [
                            {
                                "id": 0,
                                "color": {
                                    "Default": "var(--dt-colors-charts-status-ideal-default, #2f6863)"
                                },
                                "comparator": "≥",
                                "label": ""
                            },
                            {
                                "id": 1,
                                "color": {
                                    "Default": "var(--dt-colors-charts-status-warning-default, #eca440)"
                                },
                                "comparator": "≥",
                                "label": ""
                            },
                            {
                                "id": 2,
                                "color": {
                                    "Default": "var(--dt-colors-charts-status-critical-default, #c4233b)"
                                },
                                "comparator": "≥",
                                "label": "",
                                "value": 0
                            }
                        ]
                    }
                ],
                "chartSettings": {
                    "xAxisScaling": "analyzedTimeframe",
                    "gapPolicy": "gap",
                    "circleChartSettings": {
                        "groupingThresholdType": "relative",
                        "groupingThresholdValue": 0,
                        "valueType": "relative"
                    },
                    "categoryOverrides": {},
                    "curve": "linear",
                    "pointsDisplay": "auto",
                    "categoricalBarChartSettings": {
                        "layout": "horizontal",
                        "categoryAxisTickLayout": "horizontal",
                        "scale": "absolute",
                        "groupMode": "stacked",
                        "colorPaletteMode": "multi-color",
                        "categoryAxisLabel": "user",
                        "valueAxisLabel": "Lost",
                        "tooltipVariant": "single",
                        "categoryAxis": [
                            "user"
                        ],
                        "valueAxis": [
                            "Lost"
                        ]
                    },
                    "colorPalette": "categorical",
                    "valueRepresentation": "absolute",
                    "truncationMode": "middle",
                    "hiddenLegendFields": [],
                    "fieldMapping": {
                        "timestamp": "timeframe",
                        "leftAxisValues": [
                            "expected",
                            "actual"
                        ],
                        "leftAxisDimensions": [
                            "Lost"
                        ]
                    },
                    "bandChartSettings": {
                        "lower": "expected",
                        "upper": "actual",
                        "time": "timeframe"
                    }
                },
                "singleValue": {
                    "showLabel": true,
                    "label": "🚩Lost Revenue (Due to Errors)",
                    "prefixIcon": "BlockIcon",
                    "recordField": "Lost",
                    "autoscale": true,
                    "alignment": "center",
                    "trend": {
                        "trendType": "auto",
                        "isVisible": true
                    },
                    "colorThresholdTarget": "value",
                    "isIconVisible": false,
                    "sparklineSettings": {
                        "lineType": "linear",
                        "variant": "area",
                        "showTicks": false
                    }
                },
                "table": {
                    "rowDensity": "condensed",
                    "enableSparklines": false,
                    "hiddenColumns": [],
                    "linewrapEnabled": false,
                    "lineWrapIds": [],
                    "monospacedFontEnabled": false,
                    "monospacedFontColumns": [],
                    "columnWidths": {},
                    "columnTypeOverrides": []
                },
                "honeycomb": {
                    "shape": "hexagon",
                    "legend": {
                        "hidden": false,
                        "position": "auto"
                    },
                    "displayedFields": [
                        "user"
                    ],
                    "dataMappings": {
                        "value": "interval"
                    },
                    "truncationMode": "middle",
                    "colorMode": "color-palette",
                    "colorPalette": "blue"
                },
                "histogram": {
                    "legend": "auto",
                    "yAxis": {
                        "label": "Frequency",
                        "scale": "linear"
                    },
                    "colorPalette": "categorical",
                    "dataMappings": [
                        {
                            "valueAxis": "interval",
                            "rangeAxis": ""
                        },
                        {
                            "valueAxis": "Lost",
                            "rangeAxis": ""
                        }
                    ],
                    "variant": "single",
                    "truncationMode": "middle",
                    "displayedFields": []
                },
                "autoSelectVisualization": false,
                "unitsOverrides": []
            },
            "querySettings": {
                "maxResultRecords": 1000,
                "defaultScanLimitGbytes": 500,
                "maxResultMegaBytes": 1,
                "defaultSamplingRatio": 10,
                "enableSampling": false
            }
        },
        "17": {
            "type": "data",
            "title": "Order Backend Request Failures",
            "query": "timeseries sum(dt.service.request.count),filter: {matchesPhrase(entityAttr(dt.entity.service, \"entity.name\"), \"order-backend-*\")}",
            "davis": {
                "enabled": false,
                "davisVisualization": {
                    "isAvailable": true
                }
            },
            "visualization": "barChart",
            "visualizationSettings": {
                "thresholds": [
                    {
                        "id": 1,
                        "field": "Total",
                        "title": "",
                        "isEnabled": true,
                        "rules": [
                            {
                                "id": 0,
                                "color": {
                                    "Default": "var(--dt-colors-charts-categorical-color-03-default, #2a7453)"
                                },
                                "comparator": "≥",
                                "label": "",
                                "value": 0
                            },
                            {
                                "id": 1,
                                "color": {
                                    "Default": "var(--dt-colors-charts-status-warning-default, #eca440)"
                                },
                                "comparator": "≥",
                                "label": ""
                            },
                            {
                                "id": 2,
                                "color": {
                                    "Default": "var(--dt-colors-charts-status-critical-default, #c4233b)"
                                },
                                "comparator": "≥",
                                "label": ""
                            }
                        ]
                    }
                ],
                "chartSettings": {
                    "xAxisScaling": "analyzedTimeframe",
                    "gapPolicy": "gap",
                    "circleChartSettings": {
                        "groupingThresholdType": "relative",
                        "groupingThresholdValue": 0,
                        "valueType": "relative"
                    },
                    "categoryOverrides": {},
                    "curve": "linear",
                    "pointsDisplay": "auto",
                    "categoricalBarChartSettings": {
                        "layout": "horizontal",
                        "categoryAxisTickLayout": "horizontal",
                        "scale": "absolute",
                        "groupMode": "stacked",
                        "colorPaletteMode": "multi-color",
                        "categoryAxisLabel": "dt.entity.service",
                        "valueAxisLabel": "interval",
                        "tooltipVariant": "single"
                    },
                    "colorPalette": "categorical",
                    "valueRepresentation": "absolute",
                    "truncationMode": "middle",
                    "fieldMapping": {
                        "timestamp": "timeframe",
                        "leftAxisValues": [
                            "sum(dt.service.request.count)"
                        ],
                        "leftAxisDimensions": []
                    },
                    "hiddenLegendFields": [
                        "user"
                    ],
                    "legend": {
                        "hidden": true
                    },
                    "leftYAxisSettings": {
                        "isLabelVisible": false
                    },
                    "xAxisIsLabelVisible": false,
                    "seriesOverrides": [
                        {
                            "seriesId": [
                                "sum(dt.service.request.count)"
                            ],
                            "override": {
                                "color": {
                                    "Default": "var(--dt-colors-charts-loglevel-emergency-default, #ae132d)"
                                }
                            }
                        }
                    ]
                },
                "singleValue": {
                    "showLabel": true,
                    "label": "Total Revenue",
                    "prefixIcon": "",
                    "recordField": "Total",
                    "autoscale": true,
                    "alignment": "center",
                    "trend": {
                        "trendType": "auto",
                        "isVisible": true
                    },
                    "colorThresholdTarget": "background"
                },
                "table": {
                    "rowDensity": "condensed",
                    "enableSparklines": false,
                    "hiddenColumns": [],
                    "linewrapEnabled": false,
                    "lineWrapIds": [],
                    "monospacedFontEnabled": false,
                    "monospacedFontColumns": [],
                    "columnWidths": {},
                    "columnTypeOverrides": []
                },
                "honeycomb": {
                    "shape": "hexagon",
                    "legend": {
                        "hidden": false,
                        "position": "auto"
                    },
                    "displayedFields": [],
                    "dataMappings": {},
                    "truncationMode": "middle",
                    "colorMode": "color-palette",
                    "colorPalette": "blue"
                },
                "histogram": {
                    "legend": "auto",
                    "yAxis": {
                        "label": "Frequency",
                        "scale": "linear"
                    },
                    "colorPalette": "categorical",
                    "dataMappings": [
                        {
                            "valueAxis": "interval",
                            "rangeAxis": ""
                        }
                    ],
                    "variant": "single",
                    "truncationMode": "middle",
                    "displayedFields": []
                },
                "autoSelectVisualization": false
            },
            "querySettings": {
                "maxResultRecords": 1000,
                "defaultScanLimitGbytes": 500,
                "maxResultMegaBytes": 1,
                "defaultSamplingRatio": 10,
                "enableSampling": false
            }
        },
        "18": {
            "type": "data",
            "title": "",
            "query": "",
            "queryConfig": {
                "version": "11.4.0",
                "subQueries": [
                    {
                        "id": "A",
                        "isEnabled": true,
                        "datatype": "metrics",
                        "metric": {
                            "key": "",
                            "aggregation": "avg"
                        }
                    }
                ]
            },
            "subType": "dql-builder-metrics",
            "visualization": "lineChart",
            "visualizationSettings": {
                "thresholds": [],
                "chartSettings": {
                    "gapPolicy": "gap",
                    "circleChartSettings": {
                        "groupingThresholdType": "relative",
                        "groupingThresholdValue": 0,
                        "valueType": "relative"
                    },
                    "categoryOverrides": {},
                    "curve": "linear",
                    "pointsDisplay": "auto",
                    "categoricalBarChartSettings": {
                        "layout": "horizontal",
                        "categoryAxisTickLayout": "horizontal",
                        "scale": "absolute",
                        "groupMode": "stacked",
                        "colorPaletteMode": "multi-color"
                    },
                    "colorPalette": "categorical",
                    "valueRepresentation": "absolute",
                    "truncationMode": "middle"
                },
                "singleValue": {
                    "showLabel": true,
                    "label": "",
                    "prefixIcon": "",
                    "autoscale": true,
                    "alignment": "center",
                    "colorThresholdTarget": "value"
                },
                "table": {
                    "rowDensity": "condensed",
                    "enableSparklines": false,
                    "hiddenColumns": [],
                    "linewrapEnabled": false,
                    "lineWrapIds": [],
                    "monospacedFontEnabled": false,
                    "monospacedFontColumns": [],
                    "columnWidths": {},
                    "columnTypeOverrides": []
                },
                "honeycomb": {
                    "shape": "hexagon",
                    "legend": {
                        "hidden": false,
                        "position": "auto"
                    },
                    "displayedFields": [],
                    "dataMappings": {},
                    "truncationMode": "middle",
                    "colorMode": "color-palette",
                    "colorPalette": "categorical"
                },
                "histogram": {
                    "legend": "auto",
                    "yAxis": {
                        "label": "Frequency",
                        "scale": "linear"
                    },
                    "colorPalette": "categorical",
                    "dataMappings": [],
                    "variant": "single",
                    "truncationMode": "middle"
                }
            },
            "davis": {},
            "querySettings": {
                "maxResultRecords": 1000,
                "defaultScanLimitGbytes": 500,
                "maxResultMegaBytes": 1,
                "defaultSamplingRatio": 10,
                "enableSampling": false
            }
        },
        "19": {
            "type": "data",
            "title": "",
            "query": "",
            "queryConfig": {
                "version": "11.4.0",
                "subQueries": [
                    {
                        "id": "A",
                        "isEnabled": true,
                        "datatype": "metrics",
                        "metric": {
                            "key": "",
                            "aggregation": "avg"
                        }
                    }
                ]
            },
            "subType": "dql-builder-metrics",
            "visualization": "lineChart",
            "visualizationSettings": {
                "thresholds": [],
                "chartSettings": {
                    "gapPolicy": "gap",
                    "circleChartSettings": {
                        "groupingThresholdType": "relative",
                        "groupingThresholdValue": 0,
                        "valueType": "relative"
                    },
                    "categoryOverrides": {},
                    "curve": "linear",
                    "pointsDisplay": "auto",
                    "categoricalBarChartSettings": {
                        "layout": "horizontal",
                        "categoryAxisTickLayout": "horizontal",
                        "scale": "absolute",
                        "groupMode": "stacked",
                        "colorPaletteMode": "multi-color"
                    },
                    "colorPalette": "categorical",
                    "valueRepresentation": "absolute",
                    "truncationMode": "middle"
                },
                "singleValue": {
                    "showLabel": true,
                    "label": "",
                    "prefixIcon": "",
                    "autoscale": true,
                    "alignment": "center",
                    "colorThresholdTarget": "value"
                },
                "table": {
                    "rowDensity": "condensed",
                    "enableSparklines": false,
                    "hiddenColumns": [],
                    "linewrapEnabled": false,
                    "lineWrapIds": [],
                    "monospacedFontEnabled": false,
                    "monospacedFontColumns": [],
                    "columnWidths": {},
                    "columnTypeOverrides": []
                },
                "honeycomb": {
                    "shape": "hexagon",
                    "legend": {
                        "hidden": false,
                        "position": "auto"
                    },
                    "displayedFields": [],
                    "dataMappings": {},
                    "truncationMode": "middle",
                    "colorMode": "color-palette",
                    "colorPalette": "categorical"
                },
                "histogram": {
                    "legend": "auto",
                    "yAxis": {
                        "label": "Frequency",
                        "scale": "linear"
                    },
                    "colorPalette": "categorical",
                    "dataMappings": [],
                    "variant": "single",
                    "truncationMode": "middle"
                }
            },
            "davis": {},
            "querySettings": {
                "maxResultRecords": 1000,
                "defaultScanLimitGbytes": 500,
                "maxResultMegaBytes": 1,
                "defaultSamplingRatio": 10,
                "enableSampling": false
            }
        }
    },
    "layouts": {
        "0": {
            "x": 0,
            "y": 2,
            "w": 24,
            "h": 2
        },
        "3": {
            "x": 0,
            "y": 13,
            "w": 24,
            "h": 2
        },
        "9": {
            "x": 8,
            "y": 4,
            "w": 8,
            "h": 3
        },
        "10": {
            "x": 2,
            "y": 0,
            "w": 22,
            "h": 2
        },
        "11": {
            "x": 0,
            "y": 0,
            "w": 2,
            "h": 2
        },
        "13": {
            "x": 0,
            "y": 15,
            "w": 8,
            "h": 6
        },
        "14": {
            "x": 0,
            "y": 7,
            "w": 8,
            "h": 6
        },
        "15": {
            "x": 16,
            "y": 4,
            "w": 8,
            "h": 3
        },
        "16": {
            "x": 16,
            "y": 7,
            "w": 8,
            "h": 6
        },
        "17": {
            "x": 8,
            "y": 15,
            "w": 8,
            "h": 6
        },
        "18": {
            "x": 0,
            "y": 4,
            "w": 8,
            "h": 3
        },
        "19": {
            "x": 8,
            "y": 7,
            "w": 8,
            "h": 6
        }
    },
    "importedWithCode": false
}
```
</details>

Then upload the dashboard to your tenant - navigate to `Apps`, open the `Dashboards` app, and select `Upload`.

![Upload Dashboard](../../../assets/images/03-01-02-upload.png)


### 📌 Task #3: Chart a metric

**Your Task:** Configure a total revenue tile

1. Click on the first empty tile (top left) under `Business Analytics`, then the pencil icon to edit the tile.

![Edit Tile](../../../assets/images/03-01-03-pencil.png)

2. Navigate to the `Select metric` dropdown, search for `shop.revenue.actual` and select the metric.
3. Change the `avg` aggregation dropdown to `sum`.
4. Click on `Type to filter` and set the filter `user = <your-github-username>`.
    Hint: To complete the step, click outside of the filter dropdown.
5. Click on `Run` and verify the metric is being captured by Dynatrace by viewing the chart.
6. In order to use a single value tile, click on the `+` under the `Split by` and select `Reduce to single value`.

![Single value](../../../assets/images/03-01-03-single_value.png)

7. Under the `Reduce to single value` dropdown, select `Sum`. 
8. Click on `Run` again.
9. Navigate to the `Visual` tab, and select `Single value` for visualization.
10. Expand the `Single value` configuration category:
	* Edit `Label` to `Total Revenue`
11. Disable the `Trend` and `Sparkline` by toggling the slider.
12. Close out of the edit window of the tile.

<details>
  <summary>Results:</summary>
![Tile 1](../../../assets/images/03-01-03-complete.png)
</details>


### 📌 (Optional) Challenger Task

**Your Task:** Modify all of the populated tiles under `Business Analytics` by adding a filter on the new `user` attribute/dimension.

---