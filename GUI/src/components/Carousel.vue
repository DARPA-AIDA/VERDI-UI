<!--
  - Copyright 2019 Next Century Corporation/CACI
  -
  - Licensed under the Apache License, Version 2.0 (the "License");
  - you may not use this file except in compliance with the License.
  - You may obtain a copy of the License at
  -
  -       http://www.apache.org/licenses/LICENSE-2.0
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS,
  - WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  - See the License for the specific language governing permissions and
  - limitations under the License.
  -
  -->

<!--
 - The following vue component offers an effective way to quickly browse through
 - snippets of data or images in a single space. The component is located on the
 - AIDA home page at http://localhost:8081/#/ and shown in the mock-up located at
 - https://f2p9s0.axshare.com/#id=quh8s3&p=home
 -->

<template>
    <v-carousel :show-arrows="false"
                :continuous="true"
                :cycle="true"
                height="300"
                interval="6000"
                hide-delimiter-background
                class="carousel-wrapper">
        <v-carousel-item v-for="(page, index) in pages" :key="index"
                         reverse-transition="fade-transition"
                         transition="fade-transition"
                         :class="page.backgroundClass">
            <div class="carousel-content">
                <div :class="page.titleClass">{{page.title}}</div>
                <div class="carousel-description">
                    {{page.description}} <br>
                    <router-link v-if="page.link && page.linkType === 'internal'" :to="page.link">
                        <button class="carousel-button">{{page.linkText}}</button>
                    </router-link>
                    <span v-else-if="page.link && page.linkType === 'external'" @click="openExternalLink(page.link)">
                        <button class="carousel-button">{{page.linkText}}</button>
                    </span>
                </div>
            </div>
        </v-carousel-item>
    </v-carousel>
</template>

<script>

    import pageData from '@/assets/data/carousel-pages.json';
    export default {
        name: 'Carousel',
        computed: {
            pages() {
                return pageData.pages;
            }
        },
        methods: {
            openExternalLink(link) {
                window.open(link, '_blank');
            }
        }
    }
</script>

<style lang="scss" scoped>
    .carousel-wrapper{
        box-shadow: $lightweight-box-shadow;
        font-weight: 100;
    }

    .carousel-content {
        padding: 0 30px;
        display: flex;
        flex-direction: column;
    }

    .carousel-main {
        @include carousel-text;
        font-size: 40px;
        padding: 0 10px;
    }

    .carousel-title {
        @include carousel-text;
        font-size: 34px;
        padding: 0 10px 10px;
    }

    .carousel-description {
        font-size: 18px;
        color: $theme-teal !important;
        overflow-y: auto;
        max-width: 500px;
        max-height: 170px;
        padding: 0 11px 6px;
    }

    .carousel-button {
        @include submit-button;
        border-radius: 4px;
        box-shadow: $lightweight-box-shadow;
        color: $theme-teal;
        margin-top: 20px;
        font-size: initial;
    }

    .carousel-button:hover {
        color: #FFFFFF;
        background-color: $theme-teal;
    }

    .main-background {
        background: url(../assets/images/blank-background.svg) left no-repeat, url(../assets/images/main-dimensions.svg) right no-repeat;
        background-size: cover;
    }

    .explore-background {
        background: url(../assets/images/blank-background.svg) left no-repeat, url(../assets/images/explore-strains.svg) right no-repeat;
        background-size: cover;
    }

    .discover-background {
        background: url(../assets/images/blank-background.svg) left no-repeat, url(../assets/images/discover-tree.svg) right no-repeat;
        background-size: cover;
    }

    .analyze-background {
        background: url(../assets/images/blank-background.svg) left no-repeat, url(../assets/images/analyze-swirl.svg) right no-repeat;
        background-size: cover;
    }
</style>

