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
 - The following vue component is a timed tooltip shown for the purpose of alerting
 - the user that invalid data has been entered. It can be used in various components
 - that need validation. It is currently being used in the DateFilter and
 MultiSelectAutoComplete components.
 -->

<template>
    <div :ref="getRefId()" class="tooltip">
        <span :class="displayClass">
            <v-icon class="tooltip-icon">{{icon}}</v-icon>{{message}}
        </span>
    </div>
</template>

<script>
    let customToolTipElement = null;
    export default {
        name: 'ValidationTooltip',
        props: {
            showTooltip: Boolean,
            icon: String,
            message: String,
            refId: {
                type: Number,
                default: null,
                required: false
            },
            displayClass: {
                type: String,
                default: 'date'
            }
        },
        watch: {
            showTooltip(value) {
                if(value) {
                    this.flashValidationTooltip();
                }
            }
        },
        methods: {
            flashValidationTooltip() {
                customToolTipElement = this.$refs['custom_tooltip' + this.refId];
                if(customToolTipElement && !customToolTipElement.classList.contains('opacity-filled-show')) {
                    customToolTipElement.classList.add('opacity-filled-show');
                    setTimeout(() => {
                        customToolTipElement.classList.remove('opacity-filled-show');
                        this.$emit('updateShowTooltip', false);
                    } , 4000);
                }
            },
            getRefId() {
                const refTitle = 'custom_tooltip';
                return this.refId !== null ? refTitle + this.refId : refTitle;
            }
        }
    }
</script>

<style  lang="scss" scoped>

    .tooltip {
        position: relative;
        display: inline-block;
        top: 56%;
        visibility: hidden;
        opacity: 0;
        z-index: 12;
    }

    .tooltip span{
        min-width: 240px;
        background-color: #fff;
        color: #000;
        border-radius: 6px;
        border: #7c7c7c solid 1px;
        padding: 5px 8px 5px 5px;
        position: absolute;
        box-shadow: rgba(0, 0, 0, 0.2) 2px 5px 7px;
        font-family: system-ui;
    }

    .tooltip .date {
        top: -34px;
        left: 50%;
        margin-left: -60px;
    }

    .tooltip-icon {
        color: #f2a12b !important;
        font-size: 30px !important;
        margin-right: 2px;
    }

    .tooltip span::before {
        content: "";
        position: absolute;
        bottom: 100%;
        left: 10%;
        margin-left: -5px;
        border-width: 10px;
        border-style: solid;
        border-color: transparent transparent #656666 transparent;
    }

    .tooltip span::after {
        content: "";
        position: absolute;
        bottom: 97%;
        left: 10%;
        margin-left: -5px;
        border-width: 10px;
        border-style: solid;
        border-color: transparent transparent #fff transparent;
    }

</style>

