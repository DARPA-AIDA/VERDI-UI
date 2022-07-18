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

import { DATATYPES, upperFirstLetter } from './utils';
import axios from 'axios';

export const UPPERCASECATEGORY = {
  event: upperFirstLetter(DATATYPES.eventType.id),
  relation: upperFirstLetter(DATATYPES.relationType.id),
};

export const REQUEST = {
  get: 'GET',
  post: 'POST',
  delete: 'DELETE',
  put: 'PUT',
  patch: 'PATCH',
};

export const APIERRORMESSAGE = '. Please contact administrator.';
export const evalClient = axios.create({
  baseURL: location.protocol + '//' + location.hostname + ':5002/',
  timeout: 2000,
});
export const s3Client = axios.create({
  baseURL: 'https://s3.amazonaws.com/aida-blackbox-p3-media/html/',
  timeout: 2000,
});
// invoke ui with 'npx vue-cli-service serve --mode dev'
export const BASE_URL = location.protocol + '//' + location.hostname + ':' + (process.env.VUE_APP_IS_DEV ? '' : '8008') + '/api/';

export default {
  getURLParams(params = {}) {
    let ret = new URLSearchParams();
    for (const [key, value] of Object.entries(params)) {
      if (value) {
        ret.append(key, value);
      }
    }
    return ret;
  },
  getClaimFrames(filters) {
    let params = {};
    if (Object.keys(filters).length > 0) {
      params = filters;
    }

    return this.apiRequest('claimframe/search', null, REQUEST.post, JSON.stringify(params));
  },
  addLanguage(language) {
    // let request = '{ "lang": "' + language + '" }';
    let params = {}
    params['lang'] = language

    return this.apiRequest('queryClaims/language', params, REQUEST.post);
  },
  createQueryClaim(request) {
    return this.apiRequest('queryClaims', null, REQUEST.post, request);
  },
  getClaimFrameById(claimFrameId) {
    return this.apiRequest('claimframe/' + claimFrameId);
  },
  getQueryClaimListInfo() {
    return this.apiRequest('queryClaimsList');
  },
  getClaimFrameFilterValues() {
    return this.apiRequest('claimframe/topics');
  },
  getLanguages() {
    return this.apiRequest('queryClaims/language', null, REQUEST.get);
  },
  getAllQueryClaims() {
    return this.apiRequest('queryClaims');
  },
  getGraphs() {
    let params = '';
    return this.apiRequest('graphs', params, REQUEST.get);
  },
  deleteGraph(graphURI) {
    let params = {};
    params['graphURI'] = graphURI;
    return this.apiRequest('graph', params, REQUEST.delete, null);
  },
  importGraph(graphURI, blob) {
    let params = {};
    params['graphURI'] = graphURI;

    let formData = new FormData();
    formData.append('fileName', blob);

    return this.apiRequest('graph', params, REQUEST.post, formData, true);
  },
  importBatchGraph(graphURI, blob) {
    let params = {};
    params['graphURI'] = graphURI;

    let formData = new FormData();
    formData.append('compressedFile', blob);

    return this.apiRequest('graph/batch', params, REQUEST.post, formData, true);
  },
  deleteBatchGraph(graphBaseURI) {
    let params = {};
    params['graphBaseURI'] = graphBaseURI;
    return this.apiRequest('graph/batch', params, REQUEST.delete, null);
  },
  renameGraph(graphURIOrig, graphURINew) {
    let params = {};
    params['graphURIOrig'] = graphURIOrig;
    params['graphURINew'] = graphURINew;
    return this.apiRequest('graph', params, REQUEST.put, null);
  },
  deleteCache(graphURI) {
    let params = {};
    if (graphURI) {
      params['graphURI'] = graphURI;
    } else {
      params = null;
    }
    return this.apiRequest('cache', params, REQUEST.delete, null);
  },
  cacheGraph(payload) {
    return this.apiRequest('cache', null, REQUEST.post, payload);
  },
  apiRequest(api, parameters, requestType = REQUEST.get, payload = null, noContentTypeHeader = false) {
    let url = BASE_URL + api;
    let request = { method: requestType };
    let dataObject = { data: {}, statusMessage: '' };

    url += parameters ? '?' + this.getURLParams(parameters) : '';
    request['headers'] = { Accept: 'application/json' };

    if (payload) {
      request['body'] = payload;
    }

    //TODO: FIX THIS, header must be null for formData
    if (!noContentTypeHeader) {
      request['headers'] = { 'Content-Type': 'application/json; charset=utf-8' };
    }

    let promise = fetch(url, request);

    return promise
      .then((response) => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error(response.statusText);
        }
      })
      .then((response) => {
        dataObject.data = response;
        return dataObject;
      })
      .catch((error) => {
        dataObject.statusMessage = error.toString() + APIERRORMESSAGE;
        return dataObject;
      });
  },
};
