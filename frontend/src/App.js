//import logo from './logo.svg';
import './App.css';
import MyPhoto from './pao-keurope-696x464.jpg';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={MyPhoto} /*className="App-logo"*/ /*Peristrefomeno*/ alt="MyPhoto" />
        <p>
          ΠΑΝΑΘΑ ΠΑΡΕ ΤΟ 8ο
        </p>
        <p>
          
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
