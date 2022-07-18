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

const { defineConfig } = require('@vue/cli-service');
const globalSassFiles = [
    '~@/assets/styles/global.scss',
    /*commented out since these styles are now included in global*/
    //'~@/assets/styles/mixins.scss'
    //'~@/assets/styles/variables.scss'
];
const fs = require('fs'); //needed for properties-reader to work
const PropertiesReader = require('properties-reader');
const properties = PropertiesReader('interface.properties');

module.exports = defineConfig({
    publicPath: process.env.app_path
        ? '/' + process.env.app_path + '/'
        : '/',
    lintOnSave: 'default',
    devServer: {
        allowedHosts: 'all',
    },    
    css: {
        loaderOptions: {
            sass: {
                additionalData: globalSassFiles.map((src)=>'@import "' + src + '";').join('\n')
            }
        }
    },
    chainWebpack: config => {
        ["vue-modules", "normal-modules", "normal"].forEach((match) => {
            config.module.rule('sass').oneOf(match).use('sass-loader')
                .tap(opt => {
                    delete opt.additionalData;
                    return opt;
                });
        });
        config.plugin('define').tap(args =>
        {
            args[0]['process.env']['BASE_URI'] = "'" + properties.get('BASE_URI') + "'";
            return args;
        });
    }
});

