import React, { useReducer } from "react";
import ThreadContext from "./threadContext";
import threadReducer from "./threadReducer";
import axios from "axios";

import {
  GET_THREAD,
  ERROR_THREAD,
  EDIT_POST,
  ERROR_POST,
  DELETE_POST,
  CREATE_POST,
  GET_FORUM,
} from "../types";

const ThreadState = (props) => {
  const initialState = {
    //token: localStorage.getItem("token"),
    //user: [],
    thread: {},
    forum: {},
    post: {},
    posts: [],
    error: null,
  };

  const [state, dispatch] = useReducer(threadReducer, initialState);

  const getThread = async (id) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.get(
        `http://localhost:8080/ForumSystem-war/webresources/threads/${id}`,
        config
      );
      dispatch({
        type: GET_THREAD,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_THREAD, payload: err.response.data.error });
    }
  };

  const editPost = async (post) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.put(
        `http://localhost:8080/ForumSystem-war/webresources/posts/${post.id}`,
        post,
        config
      );
      dispatch({
        type: EDIT_POST,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_POST, payload: err.response.data.error });
    }
  };

  const deletePost = async (post) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.delete(
        `http://localhost:8080/ForumSystem-war/webresources/posts/${post.id}`,
        post,
        config
      );
      dispatch({
        type: DELETE_POST,
        payload: post.id,
      });
    } catch (err) {
      dispatch({ type: ERROR_POST, payload: err.response.data.error });
    }
  };

  const createPost = async (post, userId, threadId) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.post(
        `http://localhost:8080/ForumSystem-war/webresources/posts/${userId}/${threadId}`,
        post,
        config
      );
      dispatch({
        type: CREATE_POST,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_POST, payload: err.response.data.error });
    }
  };

  const getForum = async (id) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const res = await axios.get(
        `http://localhost:8080/ForumSystem-war/webresources/threads/${id}/forums`,
        config
      );
      dispatch({
        type: GET_FORUM,
        payload: res.data,
      });
    } catch (err) {
      dispatch({ type: ERROR_THREAD, payload: err.response.data.error });
    }
  };

  return (
    <ThreadContext.Provider
      value={{
        thread: state.thread,
        post: state.post,
        posts: state.posts,
        error: state.error,
        forum: state.forum,
        getThread,
        editPost,
        deletePost,
        createPost,
        getForum,
      }}
    >
      {props.children}
    </ThreadContext.Provider>
  );
};

export default ThreadState;
