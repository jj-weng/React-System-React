import { EDIT_POST, GET_POST, ERROR_POST, CREATE_POST, GET_THREAD } from "../types";

export default (state, action) => {
  switch (action.type) {
    case GET_POST:
      return {
        ...state,
        post: action.payload,
      };
    case CREATE_POST:
      return { ...state, posts: [action.payload, ...state.posts] };
    case GET_THREAD:
      return {
        ...state,
        thread: action.payload,
      };
    default:
      return state;
  }
};
