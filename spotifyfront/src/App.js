import Dashboard from './Dashboard';
import Home from './Home';


import {Routes, Route} from "react-router-dom";
function App() {
  return (
    <Routes>
      <Route path="/" element={<Home/>} />
      <Route path="/dashboard" element={<Dashboard/>} />
    </Routes>
  );
}

export default App;
