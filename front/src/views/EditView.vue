<template>
  <div class="mb-2">
    <el-input v-model="post.title"/>
  </div>
  <div class="sub d-flex">
    <div class="category">
      <el-input :value="category" />
    </div>
    <div class="regDate">
      <el-input :value="regDate" readonly/>
    </div>
  </div>
  <div class="mt-2">
    <el-input v-model="post.content" type="textarea" rows="15"></el-input>
  </div>
  <div class="mt-2">
    <div class="d-flex justify-content-end">
    <el-button type="warning" @click="edit()">글 수정완료</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
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

axios.get(`/api/posts/${props.postId}`)
      .then((response) => {
        post.value = response.data;
      })

const edit = () => {
  axios.patch(`/api/posts/${props.postId}`, post.value)
      .then(() => {
        router.replace({name:"home"});
      })
}

const category = ref("카테고리");
const regDate = ref("2023-12-31");
</script>

<style>

</style>

