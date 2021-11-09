import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Home from "./components/pages/Home";
import { Login } from "./components/pages/Login";
import { Register } from "./components/pages/Register";
import { Profile } from "./components/pages/Profile";
import { Forum } from "./components/pages/Forum";
import { Thread } from "./components/pages/Thread";
import { Post } from "./components/pages/Post";
import { User } from "./components/pages/User";
import { EditForum } from "./components/pages/EditForum";
import { EditThread } from "./components/pages/EditThread";
import { EditPost } from "./components/pages/EditPost";
import { DeleteForum } from "./components/pages/DeleteForum";
import { DeleteThread } from "./components/pages/DeleteThread";
import { DeletePost } from "./components/pages/DeletePost";
import { EditProfile} from "./components/pages/EditProfile";
import NavBar from "./components/layout/NavBar";
import "./App.css";
import UserState from "./context/user/UserState";
import ForumState from "./context/forum/ForumState";
import ThreadState from "./context/thread/ThreadState";
import PostState from "./context/post/PostState";

const App = () => {
  return (
    <UserState>
      <ForumState>
        <ThreadState>
          <PostState>
            <Router>
              <NavBar />
              <div className="App">
                <Switch>
                  <Route exact path="/" component={Home} />
                  <Route exact path="/login" component={Login} />
                  <Route exact path="/register" component={Register} />
                  <Route exact path="/profile" component={Profile} />
                  <Route exact path="/forum" component={Forum} />
                  <Route exact path="/thread" component={Thread} />
                  <Route exact path="/post" component={Post} />
                  <Route exact path="/:id/threads" component={Thread} />
                  <Route exact path="/:id/posts" component={Post} />
                  <Route exact path="/user" component={User} />
                  <Route exact path="/:id/editForum" component={EditForum} />
                  <Route exact path="/:id/editThread" component={EditThread} />
                  <Route exact path="/:id/editPost" component={EditPost} />
                  <Route exact path="/:id/deleteForum" component={DeleteForum} />
                  <Route exact path="/:id/deleteThread" component={DeleteThread} />
                  <Route exact path="/:id/deletePost" component={DeletePost} />
                  <Route exact path="/editProfile" component={EditProfile} />
                </Switch>
              </div>
            </Router>
          </PostState>
        </ThreadState>
      </ForumState>
    </UserState>
  );
};

export default App;
