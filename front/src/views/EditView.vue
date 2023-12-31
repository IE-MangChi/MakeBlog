<template>
  <div>
    <el-input v-model="post.title"/>
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
</script>

<style>

</style>

