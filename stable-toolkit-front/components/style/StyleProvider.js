import {
  AppBar,
  Box, Dialog, DialogContent,
  Drawer, Fab,
  Grid, ListItem,
  ListItemButton, MenuItem, OutlinedInput,
  Paper, Select, TextField,
  Typography
} from "@mui/material";
import {styled} from "@mui/system";

export const theme = {
  typography: {
    fontFamily: ["Cartogothic", "Arial", "sans serif"].join(","),
  },
  palette: {
    mode: "dark",
    primary: {
      main: "#0064FF",
      light: "#6B91FF",
      dark: "#003BCB",
      contractText: "#F4F4F4",
    },
    common: {
      white: "#F4F4F4",
      black: "#0F0F0F",
    },
    success: {
      main: "#008733",
      light: "#4CB85F",
      dark: "#005904",
      contractText: "#F4F4F4",
    },
    error: {
      main: "#EA1207",
      light: "#FF5A38",
      dark: "#AF0000",
      contractText: "#F4F4F4",
    },
  },
  components: {
    MuiButton: {
      defaultProps: {
        variant: "outlined"
      }
    }
  }
};

export const GridFullScreen = styled(Grid)`
  width: 100vw;
  height: 100vh;
`;

export const TypographySuccess = styled(Typography)`
  color: ${theme.palette.success.light}
`

export const GridQuarterScreen = styled(Grid)`
  width: 100%;
  height: 100%;
  left: 50%;
  top: 50%;
  position: relative;
  transform: translate(-50%, -50%);
`;

export const TypographyCenter = styled(Typography)`
  text-align: center;
  width: 100%;
`;

export const TypographyCenterCursorPointer = styled(TypographyCenter)`
  cursor: pointer;
`

export const GridMaxHeight = styled(Grid)`
  height: 100%;
`

export const StyledDrawer = styled(Drawer)`
`;

export const TitleListItem = styled(ListItem)`
width: 20rem;
`

export const StyledPaper = styled(Paper)`
  top: unset;
`;

export const StyledBox = styled(Box)`
  width: 100vw;
  height: 100vh;
  position: absolute;
  top: 0;
  right: 0;
`;

export const BottomAppBar = styled(AppBar)`
  bottom: 0;
  top: unset;
`

export const MaxWidthMenuItem = styled(MenuItem)`
  width: 100%;
`



export const DialogNoPadding = styled(DialogContent)`
  padding-top: 0;
  padding-bottom: 0;
`

export const CenterGrid = styled(Grid)`
  text-align: center;
`

export const CenterMaxHeightGrid = styled(CenterGrid)`
  height: 80vh;
  width: 80vw;
`

export const TextFieldMargin = styled(TextField)`
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
`


export const ModalPaper = styled(Paper)`
  width: 100%;
  height: 100%;
  left: 50%;
  top: 50%;
  position: relative;
  transform: translate(-50%, -50%);
`;

export const LoadingBox = styled(Box)`
  left: 50%;
  top: 50%;
  position: absolute;
  transform: translate(-50%, -50%);
`

export const GridMargin = styled(Grid)`
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
`;

export const LightTypography = styled(Typography)`
  color: ${theme.palette.primary.light}
`

export const BoxPadded = styled(Box)`
  padding: 10rem 25rem 10rem 25rem;
`

const propsOfFabs = ["fromBot", "fromLeft"];

export const FilterFab = styled(Fab, {
  shouldForwardProp: (prop) => !propsOfFabs.includes(prop),
})((props) => ({
  position: "fixed",
  left: (props.fromLeft > 0 ? props.fromLeft * 6 : 1) + "rem",
  bottom: (props.fromBot > 0 ? props.fromBot * 6 : 1) + "rem",
  flexShrink: 0
}));
