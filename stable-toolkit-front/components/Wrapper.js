import {createTheme, ThemeProvider} from "@mui/material/styles";
import Head from "next/head";
import {Fragment} from "react";
import {theme} from "./style/StyleProvider";

const createdTheme = createTheme(theme);

export const Wrapper = ({children}) => {
  return (
      <Fragment>
        <Head>
          <title>stable-toolkit-front</title>
          <meta
              name="description"
              content="stable-toolkit-front"
          />
          <link
              rel="apple-touch-icon"
              sizes="120x120"
              href="/apple-touch-icon.png"
          />
          <link
              rel="icon"
              type="image/png"
              sizes="32x32"
              href="/favicon-32x32.png"
          />
          <link
              rel="icon"
              type="image/png"
              sizes="16x16"
              href="/favicon-16x16.png"
          />
          <link rel="manifest" href="/site.webmanifest"/>
          <link rel="mask-icon" href="/safari-pinned-tab.svg" color="#5bbad5"/>
          <meta name="msapplication-TileColor" content="#da532c"/>
          <meta name="theme-color" content="#ffffff"/>

          <link rel="preconnect" href="https://fonts.googleapis.com"/>
          <link
              rel="preconnect"
              href="https://fonts.gstatic.com"
              crossOrigin="true"
          />
        </Head>
        <ThemeProvider theme={createdTheme}>{children}</ThemeProvider>
      </Fragment>
  );
};
