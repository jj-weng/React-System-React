import {
  ERROR_THREAD,
  GET_THREAD,
  EDIT_POST,
  CREATE_POST,
  DELETE_POST,
  GET_FORUM,
} from "../types";

export default (state, action) => {
  switch (action.type) {
    case GET_THREAD:
      return {
        ...state,
        thread: action.payload,
        posts: action.payload.posts,
      };
    case GET_FORUM:
      return {
        ...state,
        forum: action.payload,
      };
    case CREATE_POST:
      return { ...state, posts: [action.payload, ...state.posts] };
    case EDIT_POST:
      return {
        ...state,
        post : action.payload,
      };
    case DELETE_POST:
      return {
        ...state,
        posts: state.posts.filter((post) => post.id !== action.payload),
      };
    default:
      return state;
  }
};
