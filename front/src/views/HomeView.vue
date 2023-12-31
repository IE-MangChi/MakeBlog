<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";

const posts = ref([]);

axios.get("/api/posts?page=1&size=5").then((response) => {
  response.data.forEach((res:any) => {
    posts.value.push(res);
  })
})
</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div class="title">
        <div>
          <router-link :to="{name: 'read', params: {postId: post.id}}">{{post.title}}</router-link>
        </div>

        <div class="content">
          {{post.content}}
        </div>

        <div class="sub d-flex">
          <div class="category">
            카테고리
          </div>
          <div class="regDate">
            2023-12-31
          </div>
        </div>
      </div>
    </li>
  </ul>
</template>

<style lang="scss" scoped>
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 1.8rem;

    .title {
      a {
        text-decoration: none;
        font-size: 1.2rem;

        &:hover {
          text-decoration: underline;
        }
      }
    }

    .content {
      font-size: 0.9rem;
      margin-top: 10px;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 4px;
      font-size: 0.7rem;

      .regDate {
        margin-left: 10px;
        color: #6b6b6b;
      }
    }
  }
}

</style>