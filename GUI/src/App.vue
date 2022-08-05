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

<template>
    <div id="app" class="app-container menu-width-filter" data-app>
        <div id="app_layout" class="v-application">
            <v-app-bar app class="app-header" id="app_header">
                <router-link class="primary-nav-links" to="/">
                    <div class="program-title">
                        <img alt="AIDA Logo" class="program-icon" :src="showProgramTitle ? titleImage: iconImage">
                    </div>
                </router-link>
                <div class="primary-nav-routes">
                    <router-link class="primary-nav-links" to="/claims"><div class="primary-nav-div"><span>Claims</span></div></router-link>
                    <router-link class="primary-nav-links" to="/help/guide"><div class="primary-nav-div"><span>Help</span><v-icon>mdi-help-circle</v-icon></div></router-link>
                </div>
                <div class="primary-nav-icons">
                    <button class="light-icon-button" @click.stop="toggleProfileMenu()">
                        <v-icon id="profile_menu_icon">mdi-dots-vertical</v-icon>
                    </button>
                </div>
                <TransitionMenu ref="profile_dropdown_menu"  v-show="showProfileOptions" :menuData="profileMenuProps" @transitionMenuOptionSelected="onProfileMenuSelection"></TransitionMenu>
            </v-app-bar>

            <div id="app_content" ref="app_content" class="app-content">
                <!-- Only display search bar on views other than the home page-->
                <SecondaryNavigation v-if="routeChange(queryPageActive)" :showFilter=showFilter @updateShowFilter="showFilter = $event"></SecondaryNavigation>
                <div class="app-content-display"  :class="queryPageActive ? 'below-secondary' : ''">
                    <!-- Using vue-router to route between views-->
                    <div v-if="queryPageActive">
                        <transition name="slide-in">
                            <FilterMenu ref="filter_menu" v-show="showFilter" @updateShowFilter="showFilter = $event"></FilterMenu>
                        </transition>
                    </div>
                    <div class="full-width">
                        <router-view />
                    </div>
                </div>
            </div>

            <div ref="app_footer" class="footer-container" data-booted="true">
                <div class="footer-width-limit">
                    <div ref="footer_nav" class="footer">
                        <div class="footer-text-wrapper">
                            <div class="footer-sponsors">
                                <div  class="sponsor-wrapper">
                                    <div class="footer-icon-wrapper">
                                        <router-link class="primary-nav-links" to="/">
                                            <div class="program-title">
                                                <img alt="AIDA Logo" class="program-icon" src="@/assets/images/verdi-nocircle.svg">
                                            </div>
                                        </router-link>
                                    </div>
                                    <div class="footer-content sponsor-content">
                                        <p class="footer-header"> {{entries.sponsor.header}}:</p>
                                        <div v-for="(sponsor_content, sponsor_content_key) in entries.sponsor.contents" :key="entries.sponsor.header + '-' + sponsor_content_key">
                                            <span :class="sponsor_content.type==='title' ? 'sponsor-title' : ''">{{sponsor_content.value}}</span>
                                        </div>
                                    </div>
                                    <div class="footer-content">
                                        <div v-for="(links_content, links_content_key) in entries.sponsor_links.contents" :key="entries.sponsor_links.header + '-' + links_content_key">
                                            <a  class="sponsor-link" :href="links_content.link" target="-blank">{{links_content.value}}</a>
                                        </div>
                                    </div>
                                    <div class="footer-content">
                                        <div v-for="(site_content, site_content_key) in entries.site_info.contents" :key="site_content_key">
                                            <span>{{site_content.value}}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="footer-performers">
                                <div class="performer-wrapper">
                                    <div class="footer-content performer-content" v-for="(entry, key) in entries.performers" :key="key">
                                        <p v-if="entry.header" class="footer-header">{{entry.header}}:</p>
                                        <p v-else></p>
                                        <div v-for="(content, contentKey) in entry.contents" :key="entry.header + '-' + contentKey">
                                            <span v-if="content.type === 'text'">{{content.value}}</span>
                                            <a v-if="content.type === 'link'" :href="content.link" target="-blank">{{content.value}}</a>
                                            <a v-if="content.type === 'email'" :href="'mailto:' + content.value">{{content.value}}</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import acknowledgements from '@/assets/data/acknowledgements.json';
    import FilterMenu from '@/components/FilterMenu';
    import TransitionMenu from '@/components/TransitionMenu';
    import SecondaryNavigation from '@/components/SecondaryNavigation';
    import {VUEROUTES} from './utils';
    import titleImage from '@/assets/images/verdi-acronym.svg';
    import iconImage from '@/assets/images/verdi-lion.png';

    let requiredHomePageHeight = 920;
    let requiredWindowHeight = 1380;
    let requiredWindowWidth = 581;
    let profileElement = null;
    let footerElement = null;

    export default {
        name: 'App',
        components: {FilterMenu, TransitionMenu, SecondaryNavigation},
        data: () => ({
            showProfileOptions: false,
            showFilter: true,
            showProgramTitle: true,
            titleImage,
            iconImage,
            profileMenuProps: {
                transitionType : 'slide-fade',
                menuClasses : 'vertical-dots-menu profile-dropdown-menu',
                menuOptions : [
                    {path:'settings/graphs', type:'internal', icon: 'mdi-graphql', value: 'Graph Management', active: true},
                    // {path:'https://blackbox.verdi.nextcentury.com/', type:'external', icon: 'mdi-text-box-search-outline', value: 'Document Search', active: true},
                ]
            }
        }),
        computed: {
            entries() {
                return acknowledgements;
            },
            homePageInactive() {
                return this.$route.name !== 'home' && this.$route.name !== null;
            },
            queryPageActive() {
                return this.$route.name === VUEROUTES.claims;
            }
        },
        created() {
            //added for checking window resize
            window.addEventListener('resize', this.adjustContentOnChange);
            this.VUEROUTES = VUEROUTES;
        },
        mounted() {
            profileElement = this.$refs.profile_dropdown_menu;
            footerElement = this.$refs.app_footer;
            this.adjustContentOnChange();
        },
        destroyed() {
            window.removeEventListener('resize', this.adjustContentOnChange);
        },
        methods: {
            //check to see if v-content has a height greater than 589 then add fixed footer class
            adjustContentOnChange() {
                if(footerElement) {
                    if(((!this.homePageInactive && window.innerHeight >= requiredHomePageHeight) ||
                        (this.homePageInactive && window.innerHeight >= requiredWindowHeight)) &&
                        !footerElement.classList.contains('v-footer--fixed'))
                    {
                        footerElement.classList.add('v-footer--fixed');
                    }
                    else if (((!this.homePageInactive && window.innerHeight < requiredHomePageHeight) ||
                        (this.homePageInactive && window.innerHeight < requiredWindowHeight)) &&
                        footerElement.classList.contains('v-footer--fixed'))
                    {
                        footerElement.classList.remove('v-footer--fixed');
                    }
                }

                this.showProgramTitle = window.innerWidth >= requiredWindowWidth;
            },
            routeChange(route) {
                this.adjustContentOnChange();
                return route;
            },
            //closes the profile menu when the user clicks anywhere on the page other than the menu button
            closeProfileMenu() {
                if(profileElement && this.showProfileOptions) {
                    this.showProfileOptions = false;
                    document.removeEventListener('click', this.closeProfileMenu);
                }
            },
            onProfileMenuSelection() {
                this.closeProfileMenu();
            },
            toggleProfileMenu() {
                if(!this.showProfileOptions) {
                    document.addEventListener('click', this.closeProfileMenu);
                    this.showProfileOptions = true;
                }
                else{
                    this.closeProfileMenu();
                }
            }
        }
    };

</script>
<style lang="scss">

    .v-application{
        font-family: Arial, Helvetica, sans-serif !important;
        @include flex-column-nowrap;
    }

    .app-container {
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        color: $theme-primary-color !important;
        font-size: 14px;
        line-height: 1.5;
    }

    .menu-width-filter > .v-autocomplete__content.v-menu__content{
        min-width: unset !important;
        display: inline-flex;
        background-color: white;
        left: 6px !important;
    }

    .app-header{
        background-color: $theme-teal !important;
    }

    .v-toolbar__content{
        height: unset !important;
        overflow-x: auto;
        min-height: 64px;
    }

    .app-content {
        /*padding added to account for the primary title bar*/
        padding:$show-below-header 0 0 !important;
    }

    .app-content-display{
        min-height: $full-page-min-height;
        display: flex;
        flex: 0 0;
    }

    .below-secondary {
        /*padding added to account for the secondary nav bar*/
        padding-top: $content-below-filter;
    }

    .program-title {
        @include flex-row-nowrap;
        align-items: center;
    }

    .program-icon {
        height: 52px;
    }

    .primary-nav-div {
        height: 20px;
        display: flex;
        justify-content: space-evenly;
        align-items: baseline;
    }

    .primary-nav-div:hover {
        border-bottom: $theme-dark-light-color solid 1px;
    }

    .primary-nav-routes {
        @include flex-row-wrap;
        justify-content: space-evenly;
        align-items: center;
        flex: 0 1 380px;
        margin-left: 6px;

        .router-link-active {
            font-weight: bold;
            color: $theme-gold !important;
            .v-icon {
                color: $theme-gold !important;
            }
            .primary-nav-div:hover{
                border-bottom: $theme-gold solid 1px;
            }
        }
    }

    .primary-nav-links {
        @extend .page-link;
        color:$theme-dark-light-color;
        margin-right: 4px;
        max-width: 192px;
        font-size: 17px;

        .v-icon {
            font-size: 18px !important;
            color:$theme-dark-light-color !important;
            padding-bottom: 3px;
            padding-left: 3px;
        }
    }

    .primary-nav-links:hover{
        @include no-text-decoration;
    }

    .primary-nav-icons{
        margin-left: auto;
    }

    .primary-nav-icons.v-icon{
        color: $theme-dark-light-color !important;
    }

    .profile-dropdown-menu {
        top: 60px;
        z-index:5;
    }

    .footer-container {
        left: 0;
        right: 0;
        bottom: 0;
        align-items: center;
        display: flex;
        flex: 0 1 auto !important;
        flex-wrap: wrap;
        transition-duration: 0.2s;
        transition-property: background-color, left, right;
        transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
        color: $theme-dark-medium-color;
        justify-content: center;
        overflow-y: auto;
        max-height: 450px;
        background: url(assets/images/footer.png) left no-repeat;
        background-color: $theme-dark-offset;
        background-position: 0 100%;
        background-size: 100%;
    }

    .footer-width-limit {
        overflow-x: auto;
        width: 100%;
        display: flex;
        justify-content: center;
    }

    .footer {
        font-size: 13px;
        @include flex-row-nowrap;
        justify-content: space-evenly;
        width: 84%;
        padding: 20px 0;
        min-width: 410px;
    }

    .footer-content {
        margin: 6px 6px;
    }

    .footer-header {
        margin-bottom: 4px !important;
        font-weight: bold;
    }

    .footer-icon-wrapper{
        display: flex;
        margin: 14px 0;
    }

    .footer-text-wrapper{
        @include flex-row-nowrap;
        width: 100%;
    }

    .footer-sponsors {
        min-width: 225px;
        width: 16%;
        align-self: flex-start;
    }

    .footer-performers {
        @include flex-row-wrap;
        flex: 1 1 auto;
        align-self: center;
    }

    .sponsor-wrapper{
        @include flex-column-wrap;
    }

    .sponsor-content{
        color: $theme-gold;
        margin-top: 10px;
    }

    .sponsor-link{
        @include no-text-decoration;
        color: $theme-dark-medium-color;
        font-weight: bold;
    }

    .sponsor-link:hover {
        text-decoration: underline;
    }

    .sponsor-title {
        font-size: 11px;
    }

    .performer-wrapper{
        @include flex-row-wrap;
        justify-content: space-evenly;
        align-content: flex-start;
        flex: 1 1 auto;
    }

    .performer-content{
        width: 185px;
        min-height: 70px;
    }

    .hide-filter-menu{
        display: none;
    }
</style>
