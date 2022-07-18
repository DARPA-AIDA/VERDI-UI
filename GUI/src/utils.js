/*
 * Copyright 2019 Next Century Corporation/CACI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import Pluralize from 'pluralize';

/******************classes*******************/
class Datatype {
    constructor (id, display, text) {
        this.id = id;
        this.display = display;
        this.text = text;
    }
}

export class TableSettings {
    constructor (key, expand, heading, description, resultsPerPage = true, highlight = false, hideableColumns = false, defaultSortBy = 'headline', defaultSortDesc = false, defaultRowFilter = false) {
        this.rowKey = key;
        this.highlight = highlight;
        this.expand = expand;
        this.tableHeading = heading;
        this.tableDescription = description;
        this.showResultsPerPage = resultsPerPage;
        this.hideableColumns = hideableColumns;
        this.defaultSortBy = defaultSortBy;
        this.defaultSortDesc = defaultSortDesc;
        this.rowfilter = defaultRowFilter;
    }
}

export class HeaderConfig {
    constructor (text, align = 'start', sortable = true, value, assignWidth = true, hideable = false, linkroute = '', category = '') {
        this.text = text;
        this.align = align;
        this.sortable = sortable;
        this.value = value;
        //adjusting header row width based on content
        if(assignWidth) {
            this.width = text === 'Arguments' ? '8%' :
                align === 'center' || text === 'Date' || value === TABLEFIELDMAPPING.percentage ? '2%' :
                text === 'Description' || text === 'Statement' ? '35%' : '24%';
        }
        this.hideable = hideable;
        this.linkroute = linkroute;
        this.category = category;
    }
}

export class SecondaryExpand {
    constructor (prototype, field, headers) {
        this.prototype = prototype;
        this.field = field;
        this.headers = headers;
    }
}

/******************constant variables*******************/
export const DATATYPES = {
    argumentName : new Datatype('argument', 'Argument Name', 'argument'),
    baseGraph : new Datatype('baseGraph', 'TA3 Input', 'baseGraph'),
    topic : new Datatype('topic', 'Topics', 'topic'),
    subtopic : new Datatype('subtopic', 'Topics','subtopic'),
    template : new Datatype('template', 'Claim Templates','template'),
    queryClaimId : new Datatype('queryClaimId', 'Query Claim','description'),
    queryClaimDesc : new Datatype('description', 'Natural Language Description','description'),
    entityType : new Datatype('entity', 'Argument Type' ,'entity'),
    eventType : new Datatype('event', 'Event Type','event' ),
    dateRange : new Datatype('date', 'Date Range','date' ),
    relationType : new Datatype('relation', 'Relation Type','relation' )
};

export const COMPONENTNAMES = {
    multiSelect : 'MultiSelectAutoComplete',
    dateRange : 'DateFilter'
};


export const SELECTDISPLAY = {
    button : 'button',
    text : 'text',
    underline : 'underline',
    input: 'input'
};

export const PREFIXTYPES = {
    dash: '-',
    dot:'.',
    hash: '#',
    hashscore:'#_',
    underscore: '_',
    slash: '/'
};

export const TABLESELECTIONS = {
    display : 'selectedDisplay',
    sort : 'sort',
    sortDesc : 'descending',
    sortBy : 'by',
    paging: 'pagination',
    itemsPerPage: 'itemsPerPage',
    pageNumber: 'page',
    pageStart: 'pageStart'
};

//TODO: What do we need in this mapping
export const TABLEFIELDMAPPING = {
    //generic fields
    percentage: 'percentage',
    dates: 'dates',
    headline: 'headline',
    statement: 'statement',
    names: 'names',
    types: 'types',
    idUri: 'id_uri',
    id: 'id',
    category: 'category',
    description: 'description',
    count: 'count',
    //KE-specific fields
    ke: 'ke',
    clusterUri: 'cluster_uri',
    clusterId: 'cluster_id',
    clusterCategory: 'cluster_category',
    clusterType: 'cluster_type',
    clusterList: 'clusters',
    clusterIdList: 'cluster_ids',
    prototypeUri: 'prototype_uri',
    prototypeId: 'prototype_id',
    entityList: 'entities',
    eventList: 'events',
    relationList: 'relations',
    //claim-specific fields
    topic: 'topic',
    subtopic: 'subtopic',
    claimTemplate: 'claimTemplate',
    claimer: 'claimer',
    claimRelations: 'claimRelations',
    epistemic: 'epistemic',
    sourceDoc: 'sourceDoc',
    claimerStatement: 'claimerStatement',
    location: 'locationName',
    claimerList: 'claimers',
    variableList: 'variables',
    semantics: 'semantics',
    affiliation: 'affiliation',
    affiliationList: 'affiliations',
    provenance: 'provenance',
    claimId: 'claimId',
    xvariable: 'xVariable',
    assocKeId: 'assocKeId',
    assocKeCat: 'assocKeCat',
    assocKeTypes: 'assocKeTypes',
    sorceDocTitleExt: 'sorceDocTitleExt',



    
    //document-specific fields
    documentList: 'docs',
    docId: 'document_id',
    docTitle: 'document_title',
    docContentDate: 'document_contentdate',
    docDownloadDate: 'document_downloaddate',
    //fields specific to row highlighting and text comparison
    highlight: 'highlight',
    colorText: 'color_text',
    compare: 'compare'
};

const provenanceRoute = 'provenance';
const claimRoute = 'claims';

export const VUEROUTES = {
    doc: '/' + provenanceRoute + '/doc',
    cluster: '/cluster_details',
    element: '/element_details',
    claimDetails: '/claims/topic',
    qnode: 'https://kgtk.isi.edu/api?q=',
    claims: claimRoute
};

export const PARAMETERNAMES = {
    filter : 'filter'
};

export const LOADINGMESSAGE = 'Loading...Please wait';
export const NODATAMESSAGE = 'No data available';

export const BASEURI = process.env.BASE_URI;

/******************helper functions*******************/
export function upperFirstLetter(text) {
    return text.charAt(0).toUpperCase() + text.slice(1);
}

export function charReplace(str, char) {
    char = typeof char === 'undefined' ? '_' : char;
    return str.replace(/\s/g, char);
}

export function lowerCharReplace(str, char) {
    return charReplace(str.toLowerCase(), char);
}

export function splitOnUpper(textString) {
    return textString.split(/(?=[A-Z])/).join(' ');
}

export function pluralize(str) {
    return Pluralize(str);
}

export function prettyPluralize(str) {
    return pluralize(upperFirstLetter(str));
}

export function removePrefix(textString, type) {
    let newString = textString;

    if(textString) {
        const hashPosition = textString.lastIndexOf(PREFIXTYPES.hash);

        if (type === PREFIXTYPES.hash) {
            newString = textString.substring(hashPosition + 1, textString.length);
        } else if (type === PREFIXTYPES.hashscore) {
            let underscorePosition = textString.lastIndexOf(PREFIXTYPES.underscore);
            newString = textString.substring(hashPosition + 1, underscorePosition);
        } else if ([PREFIXTYPES.underscore, PREFIXTYPES.dot, PREFIXTYPES.dash, PREFIXTYPES.slash].includes(type)) {
            newString = textString.substring(textString.lastIndexOf(type) + 1, textString.length);
        }
    }
    return newString;
}

export function sortCaseInsensitive (array) {
    return array.sort((current, previous) => current.toLowerCase().localeCompare(previous.toLowerCase()));
}

export function sortArrayOfObjects (array, key) {
    return array.sort((a, b) => (a[key].toLowerCase() > b[key].toLowerCase()) ? 1 : -1);
}

export function arraysMatch (arrOne, arrTwo) {
    return arrOne.length === arrTwo.length &&
    arrOne.every((element) =>  arrTwo.includes(element));
}

export function buildRoute(id, path, suffix, params) {
    const encodedId = encodeURIComponent(id);
    let route = suffix ? [path, suffix, encodedId].join('/') : [path, encodedId].join('/');
    if(params && params.length > 0) {
        for (let i = 0; i < params.length; i++) {
            route += i === 0 ? '?' : '&';
            route += params[i].key + '=' + encodeURIComponent(params[i].value);
        }
    }
    return route;
}

export function isAnObject(value) {
    return typeof value === 'object';
}

export function getStringValue(value) {
    return Array.isArray(value) ?  value.join(', ') : value;
}

export function getSortedTypes(types) {
    return sortCaseInsensitive(types.map(getType));
}

export function getLastSubType(t) {
    return t.substring(t.lastIndexOf('.') + 1, t.length);
}

export function getTransformedType(t, mapping) {
    return t ? mapping[t] || splitOnUpper(getLastSubType(t)) : null;
}

export function deduplicateArray(array) {
    return [...new Map(array.map(item => [item.id, item])).values()];
}

export function getType(type) {
    return removePrefix(type, PREFIXTYPES.hash);
}

export function getRole(role) {
    return removePrefix(role, PREFIXTYPES.underscore);
}

export function makePercentage(value, precision) {
    return (value * 100).toFixed(precision);
}

export function getId(uri) {
    return removePrefix(removePrefix(uri, PREFIXTYPES.hash), PREFIXTYPES.slash)
}

/******************formatting date functions*******************/
function getDateParts(date) {
    //TODO: Is there a discrepancy here in the formatting
    if (!date) {
        return '';
    }
    const splitDate = date.split('--');
    //using provided year since all of the years in the data could not be accurately converted (e.g. 0001, 9999)
    const dateYear = splitDate[0] || '';
    const hasDash = splitDate[1].includes('-');
    const dateMonth = hasDash ? '' : new Date(null, splitDate[1], null).toLocaleString('en-us', { month: 'short' });
    const dateDay = hasDash ? splitDate[1] : splitDate[2] || '';

    return [dateYear, dateMonth, dateDay.substring(1)];
}

function combineParts(parts) {
    return parts.join(' ');
}

function combineDates(start, end) {
    let combined = '';
    if(start && end) {
        combined = start + ' \u2013 ' + end;
    }
    else {
        combined = start ? start : end;
    }
    return combined;
}

//main date function to pass unformatted date into
//TODO: Review if this is waht we want
export function getDateString(date) {
    const start = getDateParts(date.startAfter || date.startBefore);
    const end = getDateParts(date.endBefore || date.endAfter);

    const startString = start ? combineParts(start) : '';
    const endString = end ? combineParts(end) : '';

    const [sY, sM, sD] = start;
    const [eY, eM, eD] = end;

    // different years
    if (sY !== eY) {
        return combineDates(startString, endString);
    }

    // different months
    if (sM !== eM) {
        return combineDates(startString, combineParts([eM, eD]));
    }

    // different days
    if (sD !== eD) {
        return combineDates(startString, eD);
    }

    // all equal
    return startString;
}

