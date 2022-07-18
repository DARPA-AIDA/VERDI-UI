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

/**
 * This creates our Vue object and connects its router,
 * storage, and render to the object. It then calls the $mount
 * function to mount the vue to the webpage.
 */

import Vue from 'vue';
import App from './App.vue';
import store from './store';
import router from './router';
import '@mdi/font/css/materialdesignicons.css'
import Vuetify, {// a la carte
    VApp, // required
    VAppBar,
    VAutocomplete,
    VCarousel,
    VCarouselItem,
    VContainer,
    VDataIterator,
    VDataTable,
    VRow,
    VCard,
    VChip,
    VCol,
    VIcon,
    VListItem,
    VListItemAction,
    VListItemContent,
    VListItemTitle,
    VListItemIcon,
    VMenu,
    VTooltip,
    VPagination,
    VProgressLinear,
    VFileInput,
    VSelect,
    VTextField
} from 'vuetify/lib';
import { Ripple } from 'vuetify/es5/directives';
import VueSlider from 'vue-slider-component';

Vue.use(Vuetify, {
    components: {
        VApp,
        VAppBar,
        VAutocomplete,
        VCarousel,
        VCarouselItem,
        VContainer,
        VDataIterator,
        VDataTable,
        VRow,
        VCard,
        VChip,
        VCol,
        VIcon,
        VListItem,
        VListItemAction,
        VListItemContent,
        VListItemTitle,
        VListItemIcon,
        VMenu,
        VTooltip,
        VPagination,
        VProgressLinear,
        VFileInput,
        VSelect,
        VTextField,
        VueSlider
    },
    directives: {
        Ripple
    }
});

new Vue({
    router,
    el: '#app',
    store,
    mounted() {},
    render: h => h(App),
    vuetify: new Vuetify({
        theme: { disable: true },
        icons: { iconfont: 'mdi' } // default - only for display purposes
    })
}).$mount('#app');

