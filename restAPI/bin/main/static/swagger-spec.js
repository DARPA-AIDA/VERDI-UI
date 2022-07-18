window.swaggerSpec={
  "openapi" : "3.0.0",
  "info" : {
    "description" : "This is the API for the UI's access to data storage",
    "version" : "1.0.0",
    "title" : "AIDA Store REST API"
  },
  "servers" : [ {
    "url" : "{protocol}://{host}:{port}/{basePath}",
    "variables" : {
      "protocol" : {
        "enum" : [ "http", "https" ],
        "default" : "http"
      },
      "host" : {
        "default" : "localhost"
      },
      "port" : {
        "default" : "8008"
      },
      "basePath" : {
        "default" : "api"
      }
    }
  } ],
  "tags" : [ {
    "name" : "Graph",
    "description" : "Generic endpoint for viewing or manipulating graphs."
  }, {
    "name" : "Event",
    "description" : "Endpoints for retrieving events."
  }, {
    "name" : "Relation",
    "description" : "Endpoints for retrieving relations."
  }, {
    "name" : "Roles",
    "description" : "Endpoints for retrieving event / relation roles."
  }, {
    "name" : "Entity",
    "description" : "Endpoints for retrieving entities (arguments)."
  }, {
    "name" : "Hypothesis",
    "description" : "Endpoints for retrieving hypotheses including SINs."
  }, {
    "name" : "ClaimFrames",
    "description" : "Endpoints for retrieving claim frames."
  }, {
    "name" : "Docs",
    "description" : "Endpoints for retrieving source documents."
  } ],
  "paths" : {
    "/graphs" : {
      "get" : {
        "tags" : [ "Graph" ],
        "summary" : "Get a list of named graphs",
        "description" : "Get a list of available graphs",
        "operationId" : "graph",
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "headers" : {
              "Access-Control-Allow-Origin" : {
                "schema" : {
                  "type" : "string"
                },
                "description" : "CORS access"
              }
            },
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/graphs"
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/events" : {
      "get" : {
        "tags" : [ "Event" ],
        "summary" : "Return events matching provided query inputs",
        "description" : "Return events by graph and filter by multiple query inputs.",
        "operationId" : "events",
        "parameters" : [ {
          "$ref" : "#/components/parameters/graphParam"
        }, {
          "$ref" : "#/components/parameters/argumentsParam"
        }, {
          "$ref" : "#/components/parameters/typeFilter"
        }, {
          "$ref" : "#/components/parameters/limitParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/CachedEvent"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/events/types" : {
      "get" : {
        "tags" : [ "Event" ],
        "summary" : "Return event types matching provided input",
        "description" : "Return event types by graph and filter by specified input",
        "operationId" : "eventTypes",
        "parameters" : [ {
          "$ref" : "#/components/parameters/graphParam"
        }, {
          "name" : "type",
          "in" : "query",
          "description" : "Part or all of event type",
          "required" : false,
          "schema" : {
            "type" : "string"
          },
          "example" : "Conflict.Attack"
        }, {
          "$ref" : "#/components/parameters/limitParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/events/arguments" : {
      "get" : {
        "tags" : [ "Event" ],
        "summary" : "Return event argument names matching provided input",
        "description" : "Return event argument names by graph and filter by specified input",
        "operationId" : "eventNames",
        "parameters" : [ {
          "$ref" : "#/components/parameters/graphParam"
        }, {
          "$ref" : "#/components/parameters/argumentParam"
        }, {
          "$ref" : "#/components/parameters/limitParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/event" : {
      "get" : {
        "tags" : [ "Event" ],
        "summary" : "Return a single event",
        "description" : "Return event by id",
        "operationId" : "event",
        "parameters" : [ {
          "name" : "id",
          "in" : "query",
          "description" : "ID of event to retrieve",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/cluster-event-instance-IC001V5AJ-r202010162251-28"
        }, {
          "$ref" : "#/components/parameters/optionalGraphParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Event"
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/event/element" : {
      "get" : {
        "tags" : [ "Event" ],
        "summary" : "Return a single event element",
        "description" : "Return event element by id",
        "operationId" : "eventElement",
        "parameters" : [ {
          "name" : "id",
          "in" : "query",
          "description" : "ID of event element to retrieve",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/event-instance-IC001V5AJ-r202010162251-28"
        }, {
          "$ref" : "#/components/parameters/optionalGraphParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Element"
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/relations" : {
      "get" : {
        "tags" : [ "Relation" ],
        "summary" : "Return relations matching provided query inputs",
        "description" : "Return relations by graph and filter by multiple query inputs.",
        "operationId" : "relations",
        "parameters" : [ {
          "$ref" : "#/components/parameters/graphParam"
        }, {
          "$ref" : "#/components/parameters/argumentsParam"
        }, {
          "$ref" : "#/components/parameters/typeFilter"
        }, {
          "$ref" : "#/components/parameters/limitParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/CachedEvent"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/relations/types" : {
      "get" : {
        "tags" : [ "Relation" ],
        "summary" : "Return relation types matching provided input",
        "description" : "Return relation types by graph and filter by specified input",
        "operationId" : "relationTypes",
        "parameters" : [ {
          "$ref" : "#/components/parameters/graphParam"
        }, {
          "name" : "type",
          "in" : "query",
          "description" : "Part or all of relation type",
          "required" : false,
          "schema" : {
            "type" : "string"
          },
          "example" : "Physical.LocatedNear"
        }, {
          "$ref" : "#/components/parameters/limitParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/relations/arguments" : {
      "get" : {
        "tags" : [ "Relation" ],
        "summary" : "Return relation argument names matching provided input",
        "description" : "Return relation argument names by graph and filter by specified input",
        "operationId" : "relationNames",
        "parameters" : [ {
          "$ref" : "#/components/parameters/graphParam"
        }, {
          "$ref" : "#/components/parameters/argumentParam"
        }, {
          "$ref" : "#/components/parameters/limitParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/relation" : {
      "get" : {
        "tags" : [ "Relation" ],
        "summary" : "Return a single relation",
        "description" : "Return relation by id",
        "operationId" : "relation",
        "parameters" : [ {
          "name" : "id",
          "in" : "query",
          "description" : "ID of relation to retrieve",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/cluster-relation-instance-JC002YEM4-r202010162240-43"
        }, {
          "$ref" : "#/components/parameters/optionalGraphParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Event"
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/relation/element" : {
      "get" : {
        "tags" : [ "Relation" ],
        "summary" : "Return a single relation element",
        "description" : "Return relation element by id",
        "operationId" : "relationElement",
        "parameters" : [ {
          "name" : "id",
          "in" : "query",
          "description" : "ID of relation element to retrieve",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/relation-instance-JC002YEM4-r202010162240-43"
        }, {
          "$ref" : "#/components/parameters/optionalGraphParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Element"
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/roles" : {
      "get" : {
        "tags" : [ "Roles" ],
        "summary" : "Return roles",
        "description" : "Return all argument roles and fillers for a particular node",
        "operationId" : "roles",
        "parameters" : [ {
          "name" : "id",
          "in" : "query",
          "description" : "ID of relation to retrieve",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/event-instance-IC001V5AJ-r202010162251-28"
        }, {
          "$ref" : "#/components/parameters/optionalGraphParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/Role"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/entity" : {
      "get" : {
        "tags" : [ "Entity" ],
        "summary" : "Return entity",
        "description" : "Return an entity by id",
        "operationId" : "entity",
        "parameters" : [ {
          "name" : "id",
          "in" : "query",
          "description" : "ID of entity to retrieve",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/cluster-cross_doc_coref-opera-entity-combo-r202101220414-5101-entity"
        }, {
          "$ref" : "#/components/parameters/limitParam"
        }, {
          "$ref" : "#/components/parameters/optionalGraphParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/EntityDetail"
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/entity/element" : {
      "get" : {
        "tags" : [ "Entity" ],
        "summary" : "Return entity element",
        "description" : "Return an entity element by id",
        "operationId" : "entityElement",
        "parameters" : [ {
          "name" : "id",
          "in" : "query",
          "description" : "ID of entity element to retrieve",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/cross_doc_coref-opera-entity-combo-r202101220414-7659-entity"
        }, {
          "$ref" : "#/components/parameters/optionalGraphParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/EntityElement"
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/entity/decode/{name}" : {
      "get" : {
        "tags" : [ "Entity" ],
        "summary" : "Return decoded name",
        "description" : "Given an LTF encoded name, return the decoded name",
        "operationId" : "decodeName",
        "parameters" : [ {
          "name" : "name",
          "in" : "path",
          "description" : "Encoded name",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "IC001SW57:IC001SWMT:(28,0)-(33,0)"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "text/plain" : {
                "schema" : {
                  "type" : "string"
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/hypotheses" : {
      "get" : {
        "tags" : [ "Hypothesis" ],
        "summary" : "Return hypotheses",
        "description" : "Return hypotheses by graph and filter by multiple query inputs.",
        "operationId" : "hypothesesQuery",
        "parameters" : [ {
          "$ref" : "#/components/parameters/hypothesisGraphParam"
        }, {
          "$ref" : "#/components/parameters/limitParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/Hypothesis"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/hypothesis" : {
      "get" : {
        "tags" : [ "Hypothesis" ],
        "summary" : "Return hypothesis content",
        "description" : "Return hypothesis details and connections by graph and hypothesis id",
        "operationId" : "hypothesis",
        "parameters" : [ {
          "$ref" : "#/components/parameters/hypothesisGraphParam"
        }, {
          "name" : "id",
          "in" : "query",
          "description" : "ID of hypothesis to retrieve",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "http://www.utexas.edu/aida/AIDA_M36_TA3_E201_F1_hypothesis_007"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/HypothesisDetail"
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/sin/{sinId}/eventTypes" : {
      "get" : {
        "tags" : [ "Hypothesis" ],
        "summary" : "Return event types for a SIN",
        "description" : "Return event types for a SIN by SIN id. Optional argument filter can be applied.",
        "operationId" : "sinEventTypes",
        "parameters" : [ {
          "$ref" : "#/components/parameters/sinParam"
        }, {
          "name" : "entityIds",
          "in" : "query",
          "description" : "The id of the arguments that the event types will be filtered by",
          "required" : false,
          "schema" : {
            "type" : "array",
            "items" : {
              "type" : "string",
              "uniqueItems" : true,
              "example" : "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/isiEnt_GAIA_TA1_uiuc-IC001V5AX-EN_Entity_EDL_0002942"
            }
          },
          "example" : [ "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/entity-instance-K0C03BB7S-r202010162250-592", "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/isiEnt_GAIA_TA1_uiuc-IC001V5AX-EN_Entity_EDL_0002942" ]
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/sin/{sinId}/names" : {
      "get" : {
        "tags" : [ "Hypothesis" ],
        "summary" : "Return names and prototypes for entire SIN",
        "description" : "Return names and prototypes for entire SIN. Filters are mutually exclusive. If both type filters are specified, Event role takes precidence",
        "operationId" : "getNames",
        "parameters" : [ {
          "$ref" : "#/components/parameters/sinParam"
        }, {
          "name" : "eventTypes",
          "in" : "query",
          "description" : "Type of Event to limit search to",
          "required" : false,
          "schema" : {
            "type" : "array",
            "items" : {
              "type" : "string",
              "example" : "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Conflict.Attack"
            }
          },
          "example" : [ "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Conflict.Attack" ]
        }, {
          "name" : "roleTypes",
          "in" : "query",
          "description" : "Type of Event role to limit search to",
          "required" : false,
          "schema" : {
            "type" : "array",
            "items" : {
              "type" : "string",
              "uniqueItems" : true,
              "example" : "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Conflict.Attack_Target"
            }
          },
          "example" : [ "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Conflict.Attack_Target" ]
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "type" : "object",
                    "properties" : {
                      "name" : {
                        "type" : "string"
                      },
                      "entities" : {
                        "type" : "array",
                        "items" : {
                          "type" : "string"
                        }
                      }
                    }
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/sin/{sinId}/roles" : {
      "get" : {
        "tags" : [ "Hypothesis" ],
        "summary" : "Returns all role types from all TA3 output for given SIN. Can be filtered by argument Entity ID or Event type.",
        "description" : "Return all role types for SIN",
        "operationId" : "sinRoles",
        "parameters" : [ {
          "$ref" : "#/components/parameters/sinParam"
        }, {
          "name" : "prototypeIds",
          "in" : "query",
          "description" : "ID of prototype to filter by",
          "required" : false,
          "schema" : {
            "type" : "array",
            "items" : {
              "type" : "string",
              "example" : "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/isiEnt_GAIA_TA1_uiuc-K0C03BG2A-ES_Entity_EDL_0004351"
            }
          },
          "example" : [ "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/isiEnt_GAIA_TA1_uiuc-K0C03BG2A-ES_Entity_EDL_0004351" ]
        }, {
          "name" : "eventTypes",
          "in" : "query",
          "description" : "Type of Event to limit search to",
          "required" : false,
          "schema" : {
            "type" : "array",
            "items" : {
              "type" : "string",
              "example" : "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Conflict.Attack"
            }
          },
          "example" : [ "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Conflict.Attack" ]
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/sin/{sinId}/search" : {
      "post" : {
        "tags" : [ "Hypothesis" ],
        "summary" : "Filter Hypotheses by SIN, Event Type, Roles and IDs",
        "description" : "Return Hythothesis details and hypotheses filtered by claim query structured input",
        "operationId" : "hypothesisFilter",
        "parameters" : [ {
          "$ref" : "#/components/parameters/sinParam"
        } ],
        "requestBody" : {
          "description" : "Filter JSON for SIN",
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/hypothesisFilterObject"
              },
              "example" : {
                "graph" : "https://www.nextcentury.com/TA3/E201/UTEXAS-20210125/COLORADO-20210121/GAIA-20210119",
                "filters" : [ {
                  "eventType" : "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Life.Die",
                  "arguments" : [ {
                    "role" : "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Life.Die_Killer",
                    "ids" : [ "http://www.isi.edu/gaia/entities/uiuc/K0C03BF8N/EN_Entity_EDL_0007531", "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/cross_doc_coref-opera-entity-combo-dim2048-r202101240323-6384-entity" ]
                  } ]
                }, {
                  "eventType" : "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#OrganizationAffiliation.Leadership",
                  "arguments" : [ ]
                } ]
              }
            }
          }
        },
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/SinDetail"
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/sin/eventTypes" : {
      "post" : {
        "tags" : [ "Hypothesis" ],
        "summary" : "Return event types for a SIN",
        "description" : "Return event types for a SIN by SIN id. Optional argument filter can be applied.",
        "operationId" : "allEventTypes",
        "requestBody" : {
          "description" : "filter JSON to retrieve SIN event types",
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/eventTypesFilterObject"
              },
              "example" : {
                "clusterIds" : [ "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/cluster-entity-instance-JC002YBP6-r202012160402-99", "http://www.isi.edu/gaia/entities/uiuc/K0C03BB7S/ES_Entity_EDL_0004343-cluster-projectedFromSingleton" ],
                "roleTypes" : [ "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Contact.Collaborate.Meet_Participant", "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Conflict.Attack_Attacker" ]
              }
            }
          }
        },
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/sin/names" : {
      "get" : {
        "tags" : [ "Hypothesis" ],
        "summary" : "Return names and prototypes for entire SIN",
        "description" : "Return names and prototypes for entire SIN. Filters are mutually exclusive. If both type filters are specified, Event role takes precidence",
        "operationId" : "allNames",
        "parameters" : [ {
          "name" : "toComplete",
          "in" : "query",
          "description" : "String to autocomplete",
          "required" : false,
          "schema" : {
            "type" : "string"
          }
        }, {
          "name" : "eventTypes",
          "in" : "query",
          "description" : "Type of Event to limit search to",
          "required" : false,
          "schema" : {
            "type" : "array",
            "items" : {
              "type" : "string",
              "example" : "Conflict.Attack"
            }
          },
          "example" : [ "Conflict.Attack" ]
        }, {
          "name" : "roleTypes",
          "in" : "query",
          "description" : "Type of Event role to limit search to",
          "required" : false,
          "schema" : {
            "type" : "array",
            "items" : {
              "type" : "string",
              "uniqueItems" : true,
              "example" : "Target"
            }
          },
          "example" : [ "Target" ]
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "type" : "object",
                    "properties" : {
                      "id" : {
                        "type" : "string"
                      },
                      "entities" : {
                        "type" : "array",
                        "items" : {
                          "type" : "string"
                        }
                      },
                      "roles" : {
                        "type" : "array",
                        "items" : {
                          "type" : "string"
                        }
                      },
                      "eventTypes" : {
                        "type" : "array",
                        "items" : {
                          "type" : "string"
                        }
                      }
                    }
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/sin/roles" : {
      "get" : {
        "tags" : [ "Hypothesis" ],
        "summary" : "Returns all role types from all TA3 output for given SIN. Can be filtered by argument Entity ID or Event type.",
        "description" : "Return all role types for SIN",
        "operationId" : "allRoles",
        "parameters" : [ {
          "name" : "clusterIds",
          "in" : "query",
          "description" : "ID of cluster to filter by",
          "required" : false,
          "schema" : {
            "type" : "array",
            "items" : {
              "type" : "string",
              "example" : "http://www.isi.edu/gaia/entities/uiuc/K0C03BDUE/EN_Entity_EDL_0015694-cluster-projectedFromSingleton"
            }
          },
          "example" : [ "http://www.isi.edu/gaia/entities/uiuc/K0C03BDUE/EN_Entity_EDL_0015694-cluster-projectedFromSingleton" ]
        }, {
          "name" : "eventTypes",
          "in" : "query",
          "description" : "Type of Event to limit search to",
          "required" : false,
          "schema" : {
            "type" : "array",
            "items" : {
              "type" : "string",
              "example" : "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Transaction.TransferMoney"
            }
          },
          "example" : [ "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Transaction.TransferMoney" ]
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/sin/search" : {
      "post" : {
        "tags" : [ "Hypothesis" ],
        "summary" : "Filter Hypotheses by SIN, Event Type, Roles and IDs",
        "description" : "Return Hythothesis details and hypotheses filtered by claim query structured input",
        "operationId" : "sinHypothesisFilter",
        "requestBody" : {
          "description" : "Filter JSON for SIN",
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/hypothesisFilterObject"
              },
              "example" : {
                "graph" : "https://www.nextcentury.com/TA3/E201/UTEXAS-20210125/COLORADO-20210121/GAIA-20210119",
                "filters" : [ {
                  "eventType" : "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Life.Die",
                  "arguments" : [ {
                    "role" : "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Life.Die_Killer",
                    "ids" : [ "http://www.isi.edu/gaia/entities/uiuc/K0C03BF8N/EN_Entity_EDL_0007531", "http://www.lti.cs.cmu.edu/aida/opera/corpora/eval/cross_doc_coref-opera-entity-combo-dim2048-r202101240323-6384-entity" ]
                  } ]
                }, {
                  "eventType" : "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#OrganizationAffiliation.Leadership",
                  "arguments" : [ ]
                } ]
              }
            }
          }
        },
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/TA3Event"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/claimframe/search" : {
      "post" : {
        "tags" : [ "ClaimFrames" ],
        "summary" : "Retrieve Claim Frames - all or filtered",
        "description" : "Return either all Claim Frames or filtered by graph, topic and/or subtopic",
        "operationId" : "claimFrames",
        "requestBody" : {
          "description" : "Filter JSON for Claim Frame",
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/ClaimFrameFilterObject"
              },
              "example" : {
                "graph" : "https://www.nextcentury.com/TA3/CACI/CACI/CACI/CACI_claim_1",
                "topics" : [ "Hugo Chavez" ],
                "subtopics" : [ "Who was behind the killing of Hugo Chávez", "Who carried out the killing of Hugo Chávez" ]
              }
            }
          }
        },
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/ClaimFrame"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/claimframe/{claimId}" : {
      "get" : {
        "tags" : [ "ClaimFrames" ],
        "summary" : "Return claim frame details by id",
        "description" : "Return detailed information about a claim frame by querying the claim id",
        "operationId" : "claimFrameDetails",
        "parameters" : [ {
          "$ref" : "#/components/parameters/claimParam"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ClaimFrameDetail"
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/claimframe/topics" : {
      "get" : {
        "tags" : [ "ClaimFrames" ],
        "summary" : "Get topics, subtopics/claims, and claim templates.",
        "description" : "Returns all distinct topics, subtopics/claims, and claim templates.",
        "operationId" : "claimFrameTopicalList",
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/ClaimFrameTopic"
                  }
                }
              }
            }
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/claimframe/qnode/{qnodeId}" : {
      "get" : {
        "tags" : [ "ClaimFrames" ],
        "summary" : "Returns qnode deatails by id",
        "description" : "Returns qnode or pnode details from the Knowledge Graph Toolkit API based on WikiData by querying the node id",
        "operationId" : "qNodeDetails",
        "parameters" : [ {
          "name" : "qnodeId",
          "in" : "path",
          "description" : "ID of qnode or pnode to retrieve",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "Q7187"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/NodeDetail"
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/docs" : {
      "get" : {
        "tags" : [ "Docs" ],
        "summary" : "Return source documents matching provided query inputs",
        "description" : "Return source documents by graph and filter by multiple prototype ids",
        "operationId" : "docs",
        "parameters" : [ {
          "name" : "hypothesis",
          "in" : "query",
          "description" : "Which hypothesis to query",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "http://www.utexas.edu/aida/AIDA_M36_TA3_E201_F1_hypothesis_007"
        }, {
          "name" : "id",
          "in" : "query",
          "description" : "Which event or relation id to query",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "http://www.isi.edu/gaia/events/uiuc/IC001V362/ES_Event_004999"
        }, {
          "name" : "roles",
          "in" : "query",
          "description" : "The roles to filter by",
          "required" : true,
          "schema" : {
            "type" : "array",
            "items" : {
              "type" : "string",
              "uniqueItems" : true,
              "example" : "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Conflict.Attack_Attacker"
            }
          },
          "example" : [ "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Conflict.Attack_Target", "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Conflict.Attack_Attacker" ]
        }, {
          "name" : "graph",
          "in" : "query",
          "description" : "Which graph to query in",
          "required" : false,
          "schema" : {
            "type" : "string"
          },
          "example" : "https://www.nextcentury.com/TA3/E201/UTEXAS-20210125/COLORADO-20210121/GAIA-20210119"
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/Doc"
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/graph" : {
      "post" : {
        "tags" : [ "Blazegraph" ],
        "summary" : "Upload TTL and Import into Blazegraph",
        "description" : "Upload TTL and Import into Blazegraph",
        "operationId" : "graphUpload",
        "parameters" : [ {
          "name" : "graphURI",
          "in" : "query",
          "description" : "URI of graph",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "https://www.nextcentury.com/TA3/SIN_ID/TA3_TEAM_RUN_ID/TA2_TEAM_RUN_ID/TA1_TEAM_RUN_ID"
        } ],
        "requestBody" : {
          "content" : {
            "multipart/form-data" : {
              "schema" : {
                "type" : "object",
                "properties" : {
                  "fileName" : {
                    "type" : "string",
                    "format" : "binary"
                  }
                }
              }
            }
          }
        },
        "responses" : {
          "200" : {
            "description" : "Successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "object",
                  "properties" : {
                    "message" : {
                      "type" : "string"
                    }
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      },
      "delete" : {
        "tags" : [ "Blazegraph" ],
        "summary" : "Drop Blazegraph graph by URI",
        "description" : "Drop Blazegraph graph by URI",
        "operationId" : "graphDelete",
        "parameters" : [ {
          "name" : "graphURI",
          "in" : "query",
          "description" : "URI of graph",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "https://www.nextcentury.com/TA3/SIN_ID/TA3_TEAM_RUN_ID/TA2_TEAM_RUN_ID/TA1_TEAM_RUN_ID"
        } ],
        "responses" : {
          "200" : {
            "description" : "Successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "object",
                  "properties" : {
                    "message" : {
                      "type" : "string"
                    }
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      },
      "put" : {
        "tags" : [ "Blazegraph" ],
        "summary" : "Update Blazegraph graph by URI",
        "description" : "Currently only supports rename of graph by URI",
        "operationId" : "graphRename",
        "parameters" : [ {
          "name" : "graphURIOrig",
          "in" : "query",
          "description" : "URI of OLD graph",
          "required" : true,
          "schema" : {
            "type" : "string"
          }
        }, {
          "name" : "graphURINew",
          "in" : "query",
          "description" : "URI of NEW graph",
          "required" : true,
          "schema" : {
            "type" : "string"
          },
          "example" : "https://www.nextcentury.com/TA3/SIN_ID/TA3_TEAM_RUN_ID/TA2_TEAM_RUN_ID/TA1_TEAM_RUN_ID"
        } ],
        "responses" : {
          "200" : {
            "description" : "Successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "object",
                  "properties" : {
                    "message" : {
                      "type" : "string"
                    }
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    },
    "/cache" : {
      "post" : {
        "tags" : [ "ElasticSearch" ],
        "summary" : "Create cache from graph",
        "description" : "Create cache from graph",
        "operationId" : "cacheCreate",
        "requestBody" : {
          "description" : "JSON of Task Area, graph root URI, and overwrite switch",
          "required" : false,
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/CacheObject"
              }
            }
          }
        },
        "responses" : {
          "200" : {
            "description" : "Successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "object",
                  "properties" : {
                    "message" : {
                      "type" : "string"
                    }
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      },
      "delete" : {
        "tags" : [ "ElasticSearch" ],
        "summary" : "Clears cache",
        "description" : "Clears an indices from ElasticSearch",
        "operationId" : "cacheDelete",
        "parameters" : [ {
          "name" : "graphURI",
          "in" : "query",
          "description" : "Clear Cache for graphURI. If graphURI is empty, clear all cache.",
          "required" : false,
          "schema" : {
            "type" : "string"
          }
        } ],
        "responses" : {
          "200" : {
            "description" : "Successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "object",
                  "properties" : {
                    "message" : {
                      "type" : "string"
                    }
                  }
                }
              }
            }
          },
          "400" : {
            "description" : "Invalid parameters"
          },
          "404" : {
            "description" : "API not found"
          }
        }
      }
    }
  },
  "components" : {
    "schemas" : {
      "CacheObject" : {
        "type" : "object",
        "properties" : {
          "taskArea" : {
            "type" : "string",
            "example" : "TA3"
          },
          "overwrite" : {
            "type" : "boolean",
            "example" : true
          },
          "graphRootURI" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            },
            "example" : [ "https://www.nextcentury.com/TA3/E201/BBN-20210922/COLORADO-20210121/BBN-20210120", "https://www.nextcentury.com/TA3/E202/BBN-20210922/COLORADO-20210121/BBN-20210120", "https://www.nextcentury.com/TA3/E203/BBN-20210922/COLORADO-20210121/BBN-20210120" ]
          }
        }
      },
      "CachedEvent" : {
        "type" : "object",
        "properties" : {
          "cluster" : {
            "type" : "string"
          },
          "handle" : {
            "type" : "string"
          },
          "prototype" : {
            "type" : "string"
          },
          "types" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            }
          },
          "names" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            }
          },
          "argumentCount" : {
            "type" : "integer"
          }
        }
      },
      "Event" : {
        "type" : "object",
        "properties" : {
          "cluster" : {
            "type" : "string"
          },
          "handle" : {
            "type" : "string"
          },
          "category" : {
            "type" : "string"
          },
          "prototype" : {
            "$ref" : "#/components/schemas/Member"
          },
          "members" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/Member"
            }
          }
        }
      },
      "Member" : {
        "allOf" : [ {
          "$ref" : "#/components/schemas/JustifiedNode"
        }, {
          "type" : "object",
          "properties" : {
            "arguments" : {
              "type" : "array",
              "items" : {
                "$ref" : "#/components/schemas/Role"
              }
            }
          }
        } ]
      },
      "Element" : {
        "allOf" : [ {
          "$ref" : "#/components/schemas/Member"
        }, {
          "type" : "object",
          "properties" : {
            "clusters" : {
              "type" : "array",
              "items" : {
                "$ref" : "#/components/schemas/JustifiedNode"
              }
            }
          }
        } ]
      },
      "Role" : {
        "type" : "object",
        "properties" : {
          "role" : {
            "type" : "string"
          },
          "fillers" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/Entity"
            }
          }
        }
      },
      "JustifiedNode" : {
        "type" : "object",
        "discriminator" : {
          "propertyName" : "objectType"
        },
        "properties" : {
          "id" : {
            "type" : "string"
          },
          "handle" : {
            "type" : "string"
          },
          "objectType" : {
            "type" : "string",
            "enum" : [ "Entity", "Member", "Element", "EntityMember", "EntityElement", "JustifiedNode" ]
          },
          "types" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            }
          },
          "docs" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/DocObject"
            }
          }
        }
      },
      "Entity" : {
        "allOf" : [ {
          "$ref" : "#/components/schemas/JustifiedNode"
        }, {
          "type" : "object",
          "properties" : {
            "names" : {
              "type" : "array",
              "items" : {
                "type" : "string"
              }
            },
            "category" : {
              "type" : "string"
            },
            "clusterIds" : {
              "type" : "array",
              "items" : {
                "type" : "string"
              }
            }
          }
        } ]
      },
      "EntityDetail" : {
        "type" : "object",
        "properties" : {
          "cluster" : {
            "type" : "string"
          },
          "handle" : {
            "type" : "string"
          },
          "prototype" : {
            "$ref" : "#/components/schemas/EntityMember"
          },
          "members" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/EntityMember"
            }
          }
        }
      },
      "EntityMember" : {
        "allOf" : [ {
          "$ref" : "#/components/schemas/Entity"
        }, {
          "type" : "object",
          "properties" : {
            "connections" : {
              "type" : "array",
              "items" : {
                "$ref" : "#/components/schemas/ReverseRole"
              }
            }
          }
        } ]
      },
      "EntityElement" : {
        "allOf" : [ {
          "$ref" : "#/components/schemas/EntityMember"
        }, {
          "type" : "object",
          "properties" : {
            "clusters" : {
              "type" : "array",
              "items" : {
                "$ref" : "#/components/schemas/Entity"
              }
            }
          }
        } ]
      },
      "ReverseRole" : {
        "type" : "object",
        "properties" : {
          "role" : {
            "type" : "string"
          },
          "category" : {
            "type" : "string"
          },
          "connections" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/JustifiedNode"
            }
          }
        }
      },
      "Hypothesis" : {
        "type" : "object",
        "properties" : {
          "hypothesis" : {
            "type" : "string"
          },
          "entityCount" : {
            "type" : "integer"
          },
          "eventCount" : {
            "type" : "integer"
          },
          "relationCount" : {
            "type" : "integer"
          }
        }
      },
      "HypothesisDetail" : {
        "type" : "object",
        "properties" : {
          "hypothesis" : {
            "type" : "string"
          },
          "fillers" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/Entity"
            }
          },
          "members" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/Event"
            }
          },
          "docs" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/DocObject"
            }
          }
        }
      },
      "SinDetail" : {
        "type" : "object",
        "properties" : {
          "sin" : {
            "type" : "string"
          },
          "description" : {
            "type" : "string"
          },
          "hypotheses" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/CompareHypothesisDetail"
            }
          }
        }
      },
      "LDCTime" : {
        "type" : "object",
        "properties" : {
          "startBefore" : {
            "type" : "string"
          },
          "startAfter" : {
            "type" : "string"
          },
          "endBefore" : {
            "type" : "string"
          },
          "endAfter" : {
            "type" : "string"
          }
        }
      },
      "CompareHypothesisDetail" : {
        "type" : "object",
        "properties" : {
          "hypothesis" : {
            "type" : "string"
          },
          "percentage" : {
            "type" : "number"
          },
          "types" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            }
          },
          "members" : {
            "type" : "array",
            "items" : {
              "type" : "object",
              "properties" : {
                "node" : {
                  "type" : "string"
                },
                "prototype" : {
                  "type" : "string"
                },
                "category" : {
                  "type" : "string"
                },
                "type" : {
                  "type" : "string"
                },
                "dates" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/LDCTime"
                  }
                },
                "roles" : {
                  "type" : "array",
                  "items" : {
                    "type" : "object",
                    "properties" : {
                      "role" : {
                        "type" : "string"
                      },
                      "arguments" : {
                        "type" : "array",
                        "items" : {
                          "type" : "object",
                          "properties" : {
                            "id" : {
                              "type" : "string"
                            },
                            "prototype" : {
                              "type" : "string"
                            },
                            "names" : {
                              "type" : "array",
                              "items" : {
                                "type" : "string"
                              }
                            },
                            "handle" : {
                              "type" : "string"
                            },
                            "types" : {
                              "type" : "array",
                              "items" : {
                                "type" : "string"
                              }
                            },
                            "category" : {
                              "type" : "string"
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      },
      "Doc" : {
        "type" : "object",
        "properties" : {
          "queryObject" : {
            "type" : "string"
          },
          "docs" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/DocObject"
            }
          }
        }
      },
      "DocObject" : {
        "type" : "object",
        "properties" : {
          "id" : {
            "type" : "string"
          },
          "title" : {
            "type" : "string"
          },
          "contentDate" : {
            "type" : "string"
          },
          "downloadDate" : {
            "type" : "string"
          }
        }
      },
      "graphs" : {
        "type" : "array",
        "items" : {
          "type" : "string"
        },
        "example" : [ "https://www.nextcentury.com/TA2/NCC/LDC", "https://www.nextcentury.com/TA2/GAIA/M18_GAIA_1" ]
      },
      "hypothesisFilterObject" : {
        "type" : "object",
        "properties" : {
          "graph" : {
            "type" : "string"
          },
          "filters" : {
            "type" : "array",
            "items" : {
              "type" : "object",
              "properties" : {
                "eventType" : {
                  "type" : "string"
                },
                "arguments" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/hypothesisFilterArgument"
                  }
                }
              }
            }
          }
        }
      },
      "hypothesisFilterArgument" : {
        "type" : "object",
        "properties" : {
          "ids" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            }
          },
          "role" : {
            "type" : "string"
          }
        }
      },
      "eventTypesFilterObject" : {
        "type" : "object",
        "properties" : {
          "clusterIds" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            }
          },
          "roleTypes" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            }
          }
        }
      },
      "TA3Event" : {
        "type" : "object",
        "properties" : {
          "cluster" : {
            "type" : "string"
          },
          "cluster_category" : {
            "type" : "string"
          },
          "cluster_type" : {
            "type" : "string"
          },
          "id" : {
            "type" : "string"
          },
          "prototype_uri" : {
            "type" : "string"
          },
          "statement" : {
            "type" : "string"
          },
          "date" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            }
          },
          "category" : {
            "type" : "string"
          },
          "sin" : {
            "type" : "string"
          },
          "run" : {
            "type" : "string"
          },
          "hypothesis" : {
            "type" : "string"
          },
          "entities" : {
            "type" : "array",
            "items" : {
              "type" : "object",
              "properties" : {
                "headline" : {
                  "type" : "string"
                },
                "names" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                },
                "role_uri" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                },
                "role" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                },
                "id" : {
                  "type" : "string"
                },
                "prototype_uri" : {
                  "type" : "string"
                },
                "resolved_type" : {
                  "type" : "string"
                },
                "types" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                }
              }
            }
          },
          "docs" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/TA3DocObject"
            }
          }
        }
      },
      "TA3DocObject" : {
        "type" : "object",
        "properties" : {
          "document_id" : {
            "type" : "string"
          },
          "document_title" : {
            "type" : "string"
          },
          "document_contentDate" : {
            "type" : "string"
          },
          "document_downloadDate" : {
            "type" : "string"
          },
          "headline" : {
            "type" : "string"
          }
        }
      },
      "ClaimFrameTopic" : {
        "type" : "object",
        "properties" : {
          "topic" : {
            "type" : "string"
          },
          "subtopic" : {
            "type" : "string"
          },
          "template" : {
            "type" : "string"
          }
        }
      },
      "ClaimFrameFilterObject" : {
        "type" : "object",
        "properties" : {
          "graph" : {
            "type" : "string"
          },
          "topics" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            }
          },
          "subtopics" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            }
          }
        }
      },
      "ClaimFrame" : {
        "type" : "object",
        "properties" : {
          "claimId" : {
            "type" : "string"
          },
          "claimURI" : {
            "type" : "string"
          },
          "description" : {
            "type" : "string"
          },
          "topic" : {
            "type" : "string"
          },
          "subtopic" : {
            "type" : "string"
          },
          "claimTemplate" : {
            "type" : "string"
          },
          "queryId" : {
            "type" : "string"
          },
          "components" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/ClaimFrameComponentObject"
            }
          },
          "claimer" : {
            "type" : "string"
          },
          "claimerKE" : {
            "type" : "string"
          },
          "dates" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/LDCTime"
            }
          },
          "importance" : {
            "type" : "number"
          },
          "locationName" : {
            "type" : "string"
          }
        }
      },
      "ClaimFrameComponentObject" : {
        "type" : "object",
        "properties" : {
          "propertyName" : {
            "type" : "string"
          },
          "values" : {
            "type" : "array",
            "items" : {
              "type" : "object",
              "properties" : {
                "componentId" : {
                  "type" : "string"
                },
                "componentURI" : {
                  "type" : "string"
                },
                "componentName" : {
                  "type" : "string"
                },
                "componentKE" : {
                  "type" : "string"
                },
                "componentProvenance" : {
                  "type" : "string"
                },
                "componentTypes" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                },
                "system" : {
                  "type" : "string"
                }
              }
            }
          }
        }
      },
      "ClaimFrameRelationObject" : {
        "type" : "object",
        "properties" : {
          "propertyName" : {
            "type" : "string"
          },
          "values" : {
            "type" : "array",
            "items" : {
              "type" : "object",
              "properties" : {
                "claimId" : {
                  "type" : "string"
                },
                "claimURI" : {
                  "type" : "string"
                },
                "description" : {
                  "type" : "string"
                },
                "topic" : {
                  "type" : "string"
                },
                "subtopic" : {
                  "type" : "string"
                },
                "claimTemplate" : {
                  "type" : "string"
                }
              }
            }
          }
        }
      },
      "ClaimFrameProvenanceObject" : {
        "type" : "object",
        "properties" : {
          "propertyName" : {
            "type" : "string"
          },
          "values" : {
            "type" : "array",
            "items" : {
              "type" : "object",
              "properties" : {
                "keId" : {
                  "type" : "string"
                },
                "category" : {
                  "type" : "string"
                },
                "types" : {
                  "type" : "array",
                  "items" : {
                    "type" : "string"
                  }
                }
              }
            }
          }
        }
      },
      "ClaimFrameDetail" : {
        "type" : "object",
        "properties" : {
          "claimId" : {
            "type" : "string"
          },
          "claimURI" : {
            "type" : "string"
          },
          "description" : {
            "type" : "string"
          },
          "topic" : {
            "type" : "string"
          },
          "subtopic" : {
            "type" : "string"
          },
          "claimTemplate" : {
            "type" : "string"
          },
          "queryId" : {
            "type" : "string"
          },
          "dates" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/LDCTime"
            }
          },
          "importance" : {
            "type" : "number"
          },
          "sourceDocument" : {
            "$ref" : "#/components/schemas/DocObject"
          },
          "system" : {
            "type" : "string"
          },
          "epistemic" : {
            "type" : "string"
          },
          "sentiment" : {
            "type" : "string"
          },
          "components" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/ClaimFrameComponentObject"
            }
          },
          "relations" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/ClaimFrameRelationObject"
            }
          },
          "provenances" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/ClaimFrameProvenanceObject"
            }
          }
        }
      },
      "NodeDetail" : {
        "type" : "object",
        "properties" : {
          "qnode" : {
            "type" : "string"
          },
          "description" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            }
          },
          "label" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            }
          },
          "alias" : {
            "type" : "array",
            "items" : {
              "type" : "string"
            }
          },
          "data_type" : {
            "type" : "string"
          }
        }
      }
    },
    "parameters" : {
      "graphParam" : {
        "name" : "graph",
        "in" : "query",
        "description" : "Which graph to query in",
        "required" : true,
        "schema" : {
          "type" : "string"
        },
        "example" : "https://www.nextcentury.com/TA2/OPERA-20210215/OPERA-20210117"
      },
      "optionalGraphParam" : {
        "name" : "graph",
        "in" : "query",
        "description" : "Which graph to query in",
        "schema" : {
          "type" : "string"
        },
        "example" : "https://www.nextcentury.com/TA2/OPERA-20210215/OPERA-20210117"
      },
      "hypothesisGraphParam" : {
        "name" : "graph",
        "in" : "query",
        "description" : "Which graph to query in",
        "required" : true,
        "schema" : {
          "type" : "string"
        },
        "example" : "https://www.nextcentury.com/TA3/E201/UTEXAS-20210125/COLORADO-20210121/GAIA-20210119"
      },
      "argumentParam" : {
        "name" : "argument",
        "in" : "query",
        "description" : "Part or all of argument name",
        "required" : false,
        "schema" : {
          "type" : "string"
        },
        "example" : "Chavez"
      },
      "limitParam" : {
        "name" : "dataLimit",
        "in" : "query",
        "description" : "The limit of the number of data returned.",
        "required" : false,
        "schema" : {
          "type" : "integer",
          "minimum" : 0,
          "default" : 500
        }
      },
      "argumentsParam" : {
        "name" : "arguments",
        "in" : "query",
        "description" : "Participant names to filter by",
        "required" : false,
        "schema" : {
          "type" : "array",
          "items" : {
            "type" : "string",
            "example" : "Chavez",
            "uniqueItems" : true
          }
        },
        "example" : [ "Chavez" ]
      },
      "typeFilter" : {
        "name" : "types",
        "in" : "query",
        "description" : "The types to filter by",
        "required" : false,
        "schema" : {
          "type" : "array",
          "items" : {
            "type" : "string",
            "example" : "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#OrganizationAffiliation.Leadership",
            "uniqueItems" : true
          }
        },
        "example" : [ "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#Life.Die", "https://raw.githubusercontent.com/NextCenturyCorporation/AIDA-Interchange-Format/master/java/src/main/resources/com/ncc/aif/ontologies/LDCOntologyM36#OrganizationAffiliation.Leadership" ]
      },
      "sinParam" : {
        "name" : "sinId",
        "in" : "path",
        "description" : "ID of SIN to retrieve",
        "required" : true,
        "schema" : {
          "type" : "string"
        },
        "example" : "E201"
      },
      "claimParam" : {
        "name" : "claimId",
        "in" : "path",
        "description" : "ID of claim to retrieve",
        "required" : true,
        "schema" : {
          "type" : "string"
        },
        "example" : "NCC_claim_14"
      }
    }
  }
}