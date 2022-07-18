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

module.exports = {
    'env': {
        'browser': true,
        'node': true,
        'es6': true,
        'jest': true
    },
    'extends': [
        'eslint:recommended',
        'plugin:vue/essential'
    ],
    'globals': {
        'Atomics': 'readonly',
        'SharedArrayBuffer': 'readonly'
    },
    'parserOptions': {
        'parser': '@babel/eslint-parser',
        'ecmaVersion': 2020,
        'sourceType': 'module'
    },
    'plugins': [
        'vue'
    ],
    'rules': {
        //javascript
        'quotes': ['error', 'single'],
        'no-console': ['error', { allow: ['warn', 'error'] }],
        'eol-last': ['error', 'always'],
        'space-before-blocks': ['error', 'always'],
        'no-lonely-if': 'error',
        'vue/multi-word-component-names': 'off',
        'no-prototype-builtins' : 'off'
    },
    'overrides': [
    {
        'files': ['*.html'],
        'rules': {
            'quotes': ['error', 'double']
        }
    }
]
};
