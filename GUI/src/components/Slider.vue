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
 - The following vue component is a slider that can be completely customized to display
 - any number of interval ticks whether its 1-10 or counting by 20s. The API for the
 - vue-slider-component can be found at https://nightcatsama.github.io/vue-slider-component/#/api/props.
 -->

<template>
    <div class="slider-wrapper">
        <label class="field-label">{{config.min}}</label>
        <vue-slider class="range-slider"
                    @change="$emit('sliderChange', $event)"
                    :value="config.value"
                    :min="config.min"
                    :max="config.max"
                    :interval="config.interval"
                    :data="config.data"
                    :absorb="config.absorb"
                    :tooltip="'always'"
                    :tooltip-placement="['bottom']">
        </vue-slider>
        <label class="field-label">{{config.max}}</label>
    </div>
</template>

<script>


    export default {
        name: 'Slider',
        props: {
            config: Object
        }
    }
</script>

<style lang="scss" scoped>
    .slider-wrapper {
        @include flex-row-nowrap;
        justify-content: space-between;
        flex: 0 1 100%;
        width:100%;
        padding: 6px 0;
    }

    .range-slider {
        width:84% !important;
        padding-right: 4px !important;
    }
</style>

