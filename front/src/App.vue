<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import { ArrowRight } from '@element-plus/icons-vue'
</script>

<template>

  <div class="mb-2">
    <el-breadcrumb :separator-icon="ArrowRight">
      <el-breadcrumb-item :to="{ path: '/' }">홈 화면</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/write' }">글 작성</el-breadcrumb-item>
    </el-breadcrumb>
  </div>

  <RouterView />
</template>

<style scoped>

</style>