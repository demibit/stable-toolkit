import "../styles/globals.css";
import {Wrapper} from "./../components/Wrapper";
import {CookiesProvider} from "react-cookie";

function MyApp({Component, pageProps}) {
  return (

      <CookiesProvider>
        <Wrapper>
          <Component {...pageProps} />
        </Wrapper>
      </CookiesProvider>

  );
}

export default MyApp;
