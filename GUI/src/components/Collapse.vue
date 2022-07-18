<!--
 - The following vue component displays data in an expandable/collapsible accordion style.
 - The component is located on the Graph Management page in the UI
 - at http://localhost:8081/#/settings/graphs
 -->
<template>
  <div class='collapse'>
    <div @click='show = !show' class='header'>
      <slot name='header'></slot>
      <v-icon v-if='!show' class="show-icon light-icon-button">mdi-chevron-down</v-icon>
      <v-icon v-else class="show-icon light-icon-button">mdi-chevron-up</v-icon>
    </div>
    <transition>
      <div v-if='show' class='collapse-content'>
        <slot name='content'></slot>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'Collapse',
  data() {
    return {
      show: false,
    };
  },
};
</script>

<style lang='scss' scoped>
$border-color: #dee2e6;
.collapse {
  box-shadow: $below-box-shadow;
}

.collapse-content {
  padding: 1rem;
  border-right: 1px solid $border-color;
  border-left: 1px solid $border-color;
  border-bottom: 1px solid $border-color;
}
.header {
  cursor: pointer;
  user-select: none;
  padding: 0.5rem 1rem;
  border: 1px solid $border-color;
  background-color: $background-pale-teal;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-top-left-radius: 4px !important;
  border-top-right-radius: 4px !important;
}
</style>
