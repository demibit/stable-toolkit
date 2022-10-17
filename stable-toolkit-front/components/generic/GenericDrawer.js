import {Divider, List} from "@mui/material";
import React from "react";
import {StyledDrawer, StyledPaper} from "../../components/style/StyleProvider";

const GenericDrawer = ({children, open}) => {
  return (
      <StyledDrawer
          variant="persistent"
          anchor="left"
          open={open}
      >
        <Divider/>
        <List>
          {children}
          <Divider/>
        </List>

      </StyledDrawer>
  );
};

export default GenericDrawer;
