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
    <div class="homepage">
        <Carousel class="homepage-intro"></Carousel>
    </div>
</template>

<script>
    import Carousel from '@/components/Carousel';

    export default {
        components: { Carousel },
    };
</script>
<style  lang="scss">
    .homepage {
        display: flex;
        width: 100%;
        margin: 0;
        position: absolute;
        top: 6%;
        transform: translateY(50%);
    }

    .homepage-intro {
        width: 70%;
        @include flex-column-nowrap;
        margin: auto;
    }

    .homepage-title{
        color: $theme-teal;
        font-size: 19px;
        font-weight: 500;

    }
</style>
