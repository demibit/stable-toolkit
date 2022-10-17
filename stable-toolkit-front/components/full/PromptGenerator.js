import React, {Fragment, useState} from 'react';
import GenericDrawer from "../generic/GenericDrawer";
import {
  Button,
  Grid,
  ListItem,
  ListItemButton,
  Slider,
  TextField,
  Typography
} from "@mui/material";
import {pages} from "../../pages";
import {
  BoxPadded,
  CenterGrid,
  GridMargin,
  TypographyCenter
} from "../style/StyleProvider";
import GenericForm from "../generic/GenericForm";
import {handleChange} from "../util/Utilities";

const samplers = [
  {
    value: 'EULER_A',
    label: 'Euler a',
  },
  {
    value: 'DDIM',
    label: 'DDIM',
  }
];

const PromptGenerator = ({setCurrentPage}) => {
  const [prompts, setPrompts] = useState("");
  const [negativePrompts, setNegativePrompts] = useState("");
  const [sampleSteps, setSampleSteps] = useState([20, 30]);
  const [samplers, setSamplers] = useState([]);
  const [cfg, setCfg] = useState([8, 10]);
  const [denoise, setDenoise] = useState([0.6, 0.7]);
  const [numberOfPromptsToGenerate, setNumberOfPromptsToGenerate] = useState(
      128);

  return (
      <Fragment>
        <GenericDrawer>
          <ListItem>
            <ListItemButton onClick={(e) => setCurrentPage(pages.IMAGE_VIEWER)}>
              <TypographyCenter>
                Image Viewer
              </TypographyCenter>
            </ListItemButton>
          </ListItem>
        </GenericDrawer>

        <GenericForm>
          <BoxPadded>
            <Grid container>
              <GridMargin item xs={"12"}>
                <Typography variant={"h5"}>
                  Prompts
                </Typography>
              </GridMargin>
              <GridMargin item xs={"12"}>
                <TextField fullWidth multiline value={prompts}
                           onChange={e => handleChange(e, setPrompts)}/>
              </GridMargin>

              <GridMargin item xs={"12"}>
                <Typography variant={"h5"}>
                  Negative Prompts
                </Typography>
              </GridMargin>
              <GridMargin item xs={"12"}>
                <TextField fullWidth multiline value={negativePrompts}
                           onChange={e => handleChange(e, setNegativePrompts)}/>
              </GridMargin>

              {/*<GridMargin item xs={"6"}>*/}
              {/*  <TextField>*/}
              {/*    */}
              {/*  </TextField>*/}

              {/*</GridMargin>*/}


              <GridMargin item xs={"12"}>
                <Typography variant={"h5"}>
                  Sample steps: {Math.min(...sampleSteps)}-{Math.max(
                    ...sampleSteps)}
                </Typography>
              </GridMargin>
              <GridMargin item xs={"12"}>
                <Slider
                    value={sampleSteps}
                    valueLabelDisplay="off"
                    onChange={(e) => handleChange(e, setSampleSteps)}
                    min={10}
                    max={100}
                />
              </GridMargin>


              <GridMargin item xs={"12"}>
                <Typography variant={"h5"}>
                  CFG Scale: {Math.min(...cfg)}-{Math.max(...cfg)}
                </Typography>
              </GridMargin>
              <GridMargin item xs={"12"}>
                <Slider
                    value={cfg}
                    valueLabelDisplay="off"
                    onChange={(e) => handleChange(e, setCfg)}
                    min={4}
                    max={30}
                />
              </GridMargin>

              <GridMargin item xs={"12"}>
                <Typography variant={"h5"}>
                  Denoise (if Highres. Fix is enabled): {Math.min(
                    ...denoise)}-{Math.max(...denoise)}
                </Typography>
              </GridMargin>
              <GridMargin item xs={"12"}>
                <Slider
                    value={denoise}
                    valueLabelDisplay="off"
                    onChange={(e) => handleChange(e, setDenoise)}
                    min={0.1}
                    max={1}
                />
              </GridMargin>

              <GridMargin item xs={"12"}>
                <Typography variant={"h5"}>
                  Number of prompts to generate: {numberOfPromptsToGenerate}
                </Typography>
              </GridMargin>
              <GridMargin item xs={"12"}>
                <Slider
                    value={numberOfPromptsToGenerate}
                    valueLabelDisplay="off"
                    onChange={(e) => handleChange(e, setNumberOfPromptsToGenerate)}
                    min={1}
                    max={1024}
                />
              </GridMargin>

              <GridMargin item xs={"12"}>
                <CenterGrid item>
                  <Button>
                    <Typography>
                      Generate
                    </Typography>
                  </Button>
                </CenterGrid>
              </GridMargin>
            </Grid>
          </BoxPadded>
        </GenericForm>

      </Fragment>
  );
};

export default PromptGenerator;
