import {GridFullScreen,} from "../components/style/StyleProvider";

import React, {useState} from 'react';
import ImageViewer from "../components/full/ImageViewer";

export const pages = {
  IMAGE_VIEWER: "image-viewer",
}

const Home = () => {

  const [currentPage, setCurrentPage] = useState(pages.IMAGE_VIEWER);

  return (
      <GridFullScreen
          container
          direction="column"
          justifyContent="center"
          alignItems="center"
      >
        {currentPage === pages.IMAGE_VIEWER && <ImageViewer
            setCurrentPage={setCurrentPage}/>}
      </GridFullScreen>
  );
};

export default Home;
