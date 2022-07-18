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
 - The following vue file was intended to be used as a draggable resizable pop-up
 - display in the UI. The use of vue slots makes it very flexible for showing any type
 - of data. See more information regarding slots here https://vuejs.org/v2/guide/components-slots.html
 -->
<template>
    <VueDraggableResizable drag-handle=".drag"
            :class-name="'draggable dialog-box'"
            :w="740" :h="610" :x="540" :y="100" :z="10"
            v-show="showDialog"
            @dragging="onDrag"
            :parent="true"
    >
        <div class="dialog-content">
            <div class="dialog-breadcrumbs drag">
                <div><span><slot name="title"></slot></span><slot name="breadcrumbs"></slot></div>
                <button class="dark-icon-button" @click.stop="$emit('closeDialog')"><v-icon class="table-header-icon">mdi-close-box-outline</v-icon></button>
            </div>
            <slot name="description"></slot>
            <slot name="body-label"></slot>
            <slot name="body"></slot>
        </div>
    </VueDraggableResizable>
</template>

<script>
    import VueDraggableResizable from 'vue-draggable-resizable';

    export default {
        name: 'Dialog',
        components: {VueDraggableResizable},
        props: {
            showDialog: {
                type: Boolean,
                default: false
            },
        },
        data: () => ({
            width: 0,
            height: 0,
            x: 0,
            y: 0
        }),
        methods: {
            onResize: function (x, y, width, height) {
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
            },
            onDrag: function (x, y) {
                this.x = x;
                this.y = y;
            }
        }
    }
</script>

<style lang="scss" scoped>
    .dialog-box {
        position:fixed;
        top:20px;
        touch-action: none;
        box-sizing: border-box;
        background-color: white;
        box-shadow: $heavyweight-box-shadow;
        border: solid 2px $theme-light-teal-transparent;
        border-radius: 6px;
    }
    .dialog-content {
        width: 90%;
        margin: auto;
        height: 100%;
        padding: 14px 0;
    }
    .dialog-breadcrumbs {
        @include flex-row-nowrap;
        @extend .bold-text;
        font-size: 16px;
        padding: 12px 0 20px;
        width: 100%;
        justify-content: space-between;
        align-items: baseline;

        div {
            @include flex-row-nowrap;
            width: 90%;
            align-items: baseline;
        }

        div > span {
            padding-right: 6px;
        }
    }
    .draggable:active {
        cursor: grabbing;
    }
</style>

