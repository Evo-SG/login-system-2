import './App.css';
import {
    HashRouter, Redirect, Route, Switch
} from 'react-router-dom';
import 'antd/dist/antd.css';
import Login from "./pages/Login";
import Admin from "./pages/Admin";
import User from "./pages/User";
import PrivateRoute from "./components/PrivateRoute";


function App() {
    return (
      <HashRouter>
          <Switch>
              <Route path='/login' component={Login}/>
              <PrivateRoute path='/admin' permission='admin'>
                  <Admin/>
              </PrivateRoute>
              <PrivateRoute path='/user' permission='user'>
                  <User/>
              </PrivateRoute>
              {/*default route*/}
              <Route
                  render={() => <Redirect to='/login'/>}
              />
          </Switch>
      </HashRouter>
    );
}

export default App;
