<script setup lang="ts">
import {ref, onMounted} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,
  }
})
const router = useRouter();
const post = ref({});

const moveToEdit = () => {
  router.push({name:'edit', params: {postId: props.postId}});
}

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`)
      .then((response) => {
        post.value = response.data;
      })
})

const category = ref("카테고리");
const regDate = ref("2023-12-31");
</script>

<template>
  <div class="mb-2">
    <el-input v-model="post.title" readonly/>
  </div>
  <div class="sub d-flex">
    <div class="category">
      <el-input :value="category" readonly/>
    </div>
    <div class="regDate">
      <el-input :value="regDate" readonly/>
    </div>
  </div>
  <div class="mt-2">
    <el-input v-text="post.content" type="textarea" rows="15" readonly></el-input>
  </div>
  <div class="mt-2">
    <div class="d-flex justify-content-end">
      <el-button type="warning" @click="moveToEdit()">글 수정</el-button>
    </div>
  </div>


</template>

<style lang="scss" scoped>

</style>

