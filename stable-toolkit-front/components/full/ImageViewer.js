import React, {Fragment, useEffect, useState} from "react";
import {api} from "../api/api";
import GenericDrawer from "../generic/GenericDrawer";
import {
  Checkbox,
  Chip,
  Dialog,
  Divider,
  Grid,
  IconButton,
  ImageList,
  ImageListItem,
  ImageListItemBar,
  InputAdornment,
  ListItem,
  ListItemButton,
  ListItemText,
  MenuItem,
  Slider,
  TextField,
  ToggleButton,
  ToggleButtonGroup,
  Typography
} from "@mui/material";
import Image from "next/image";
import {handleChange, isImageLoaded} from "../util/Utilities";
import {
  CenterGrid,
  CenterMaxHeightGrid,
  DialogNoPadding,
  FilterFab,
  GridMargin,
  GridMaxHeight,
  StyledBox,
  TextFieldMargin,
  TitleListItem,
  TypographyCenter
} from "../style/StyleProvider";
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline';
import SettingsIcon from '@mui/icons-material/Settings';
import KeyboardArrowLeftIcon from '@mui/icons-material/KeyboardArrowLeft';
import FilterListIcon from '@mui/icons-material/FilterList';
import FolderIcon from '@mui/icons-material/Folder';
import InfiniteScroll from "react-infinite-scroll-component";
import DeleteIcon from '@mui/icons-material/Delete';

const folderTypes = [
  {
    value: 'SOURCE',
    label: 'Source',
  },
  {
    value: 'INDEX',
    label: 'Index',
  }
];

const defaultNumberOfImagesToShow = 50;
const defaultScale = 6;

const ImageViewer = ({setCurrentPage}) => {
  const [possibleFilters, setPossibleFilters] = useState(
      {
        location: [],
        tags: [],
        steps: [0, 0],
        sampler: [],
        denoise: [0, 0],
        cfg: [0, 0],
        modelHash: [],
        faceRestoration: [],
        hypernet: [],
        clipSkip: [0, 0],
        width: [0, 0],
        height: [0, 0],
        afterDate: "",
        beforeDate: ""
      });
  const [selectedFilters, setSelectedFilters] = useState(
      {
        location: [],
        tags: [],
        steps: [],
        sampler: [],
        denoise: [],
        cfg: [],
        modelHash: [],
        faceRestoration: [],
        hypernet: [],
        clipSkip: [],
        width: [],
        height: [],
        afterDate: "",
        beforeDate: ""
      });

  const [images, setImages] = useState([]);
  const [selectedImages, setSelectedImages] = useState([]);

  const [tagToAdd, setTagToAdd] = useState("");

  const [folders, setFolders] = useState([]);

  const [numberOfImagesToShow, setNumberOfImagesToShow] = useState(
      defaultNumberOfImagesToShow);

  const [scale, setScale] = useState(defaultScale);

  const [imageToDisplay, setImageToDisplay] = useState(null);

  const [showImage, setShowImage] = useState(false);
  const [showSettingsDrawer, setShowSettingsDrawer] = useState(false);
  const [showFiltersDrawer, setShowFiltersDrawer] = useState(false);

  const [selectedTagGroup, setSelectedTagGroup] = useState('add');
  const [selectedLocationGroup, setSelectedLocationGroup] = useState('add');

  const [folderType, setFolderType] = useState("");
  const [folderPath, setFolderPath] = useState("");

  const getImageFilters = () => {
    api.getImageFilter().then(value => {
      if (value.location.length > 0) {
        setPossibleFilters(value)

        if (selectedFilters.steps.length === 0) {
          setSelectedFilters({
            location: [],
            tags: [],
            steps: value.steps,
            sampler: [],
            denoise: value.denoise,
            cfg: value.cfg,
            modelHash: [],
            faceRestoration: [],
            hypernet: [],
            clipSkip: value.clipSkip,
            width: value.width,
            height: value.height,
            afterDate: new Date().getDate(),
            beforeDate: new Date().getDate()
          })
        }
      }
    });
  }

  const getImages = () => {
    const query = {
      count: numberOfImagesToShow
    }

    if (selectedFilters.location.length > 0) {
      query.location = selectedFilters.location.map(f => f.path)
    }

    if (selectedFilters.tags.length > 0) {
      query.tags = selectedFilters.tags
    }

    if (selectedFilters.steps.length > 0) {
      query.steps = selectedFilters.steps
    }

    if (selectedFilters.sampler.length > 0) {
      query.sampler = selectedFilters.sampler
    }

    if (selectedFilters.denoise.length > 0) {
      query.denoise = selectedFilters.denoise
    }

    if (selectedFilters.cfg.length > 0) {
      query.cfg = selectedFilters.cfg
    }

    if (selectedFilters.modelHash.length > 0) {
      query.modelHash = selectedFilters.modelHash
    }

    if (selectedFilters.faceRestoration.length > 0) {
      query.faceRestoration = selectedFilters.faceRestoration
    }

    if (selectedFilters.hypernet.length > 0) {
      query.hypernet = selectedFilters.hypernet
    }

    if (selectedFilters.clipSkip.length > 0) {
      query.clipSkip = selectedFilters.clipSkip
    }

    if (selectedFilters.width.length > 0) {
      query.width = selectedFilters.width
    }

    if (selectedFilters.height.length > 0) {
      query.height = selectedFilters.height
    }

    if (selectedFilters.afterDate.length > 0) {
      query.beforeDate = new Date(selectedFilters.afterDate).toISOString()
    }

    if (selectedFilters.beforeDate.length > 0) {
      query.beforeDate = new Date(selectedFilters.beforeDate).toISOString()
    }

    api.postImageFilter(query).then(value => {
      setImages(value)
    })
  }

  const putImages = (e) => {
    const folderPathToMoveTo = e.target.value

    const imagesToMove = selectedImages.map(image => {
      return {
        ...image,
        location: folderPathToMoveTo
      }
    })

    api.putImage(imagesToMove).then(value => getImages())
  }

  const getTags = () => {
    api.getTag().then(value => setTags(value))
  }

  const putTag = () => {
    api.putTag([tagToAdd]).then(value => getImageFilters())
  }

  const deleteTags = (value) => {
    api.deleteTag([value]).then(value => getImageFilters())
  }

  const getFolders = () => {
    api.getFolder().then(value => setFolders(value))
  }

  const putFolders = () => {
    const splitPath = folderPath.split("\\")

    const foldersToPut = [{
      name: splitPath[splitPath.length - 1],
      path: folderPath,
      folderType: folderType
    }]

    api.putFolder(foldersToPut).then(value => {
      setFolderPath("")
      setFolderType("")
      getFolders()
    });
  }

  const deleteFolders = (e) => {

    api.deleteFolder([e.target.value]).then(value => {
      getFolders()
    })
  }

  const selectAll = (e) => {
    e.preventDefault()

    selectedImages.length === images.length ? setSelectedImages([])
        : setSelectedImages(images)
  }

  const shortenFileName = (fileNameToShorten) => {
    const splitFileName = fileNameToShorten.split(".")
    if (fileNameToShorten.length > 20) {
      return splitFileName[0].substring(0, 17) + "..."
    }
    return splitFileName[0];
  }

  const reIndex = () => {
    api.triggerReIndex().then(value => getImages())
  }

  useEffect(() => {
    getImageFilters();
    getFolders();
    getImages();

    const interval = setInterval(() => {
      getImageFilters();
      getFolders();
      getImages();
    }, 10000);


    return () => {
      clearInterval(interval);
    };
  }, [numberOfImagesToShow]);

  return (
      <StyledBox>
        <InfiniteScroll next={() => {
          setNumberOfImagesToShow(
              numberOfImagesToShow + defaultNumberOfImagesToShow)
          getImages();
        }} hasMore={numberOfImagesToShow <= images.length}
                        loader={<TypographyCenter>Loading...</TypographyCenter>}
                        dataLength={images.length}>

          <ImageList cols={6} gap={0} id={"imageList"}>
            {images.map((image, index) => (
                isImageLoaded(image) &&
                <ImageListItem item={"true"} key={index}>
                  <Image
                      id={image.fileName}
                      src={`${process.env.LOCALHOST8080}/image?url=${image.location.replaceAll(
                          "\\", "/")}/${image.fileName}`}
                      alt=""
                      layout="intrinsic"
                      width={image.width}
                      height={image.height}
                      loading="lazy"
                      onClick={(e) => {
                        setImageToDisplay(image)
                        setShowImage(true)
                      }}

                  />
                  <ImageListItemBar
                      position="below"
                      title={
                        <Grid container
                              direction="row"
                              justifyContent="center"
                              alignItems="center">
                          <Grid item xs={10}>
                            <TypographyCenter>
                              {shortenFileName(image.fileName)}
                            </TypographyCenter>
                          </Grid>
                          <Grid item xs={2}>
                            <Checkbox checked={selectedImages.indexOf(
                                image) > -1}
                                      onClick={(e) => {
                                        selectedImages.includes(image) ?
                                            setSelectedImages(
                                                selectedImages.filter(
                                                    value => value !== image))
                                            :
                                            setSelectedImages(
                                                [...selectedImages, image])
                                      }}
                            />
                          </Grid>
                        </Grid>
                      }
                  />
                </ImageListItem>
            ))}
          </ImageList>
        </InfiniteScroll>

        <GenericDrawer open={showSettingsDrawer}>
          <TitleListItem>
            <ListItemText>
              <Typography>
                Images currently displayed: {images.length}
              </Typography>
            </ListItemText>
          </TitleListItem>

          <Divider/>

          <ListItem>
            <ListItemText>
              <Typography>Location</Typography>
            </ListItemText>
          </ListItem>

          <ListItem>
            <ToggleButtonGroup
                color="primary"
                value={selectedLocationGroup}
                exclusive
                onChange={(e) => handleChange(e, setSelectedLocationGroup)}
                fullWidth
            >
              <ToggleButton value="add">Add</ToggleButton>
              <ToggleButton value="delete">Delete</ToggleButton>
            </ToggleButtonGroup>
          </ListItem>

          {selectedLocationGroup === 'add' &&
              <Fragment>
                <ListItem>
                  <TextField
                      select
                      label="Type"
                      value={folderType}
                      onChange={(e) => {
                        setFolderType(e.target.value)
                      }}
                      fullWidth
                  >
                    {folderTypes.map((option, index) => (
                        <MenuItem key={index} value={option.value}>
                          {option.label}
                        </MenuItem>
                    ))}
                  </TextField>
                </ListItem>
                <ListItem>
                  <TextField
                      label="Path"
                      value={folderPath}
                      onChange={(e) => {
                        handleChange(e, setFolderPath)
                      }}
                      helperText="Absolute path of folder in the style C:\folder"
                      fullWidth/>
                </ListItem>
                <ListItem>
                  <ListItemButton onClick={() => putFolders()}>
                    <TypographyCenter>
                      Add
                    </TypographyCenter>
                  </ListItemButton>
                </ListItem>
              </Fragment>
          }

          {selectedLocationGroup === 'delete' &&
              <ListItem>
                <TextField
                    select
                    label="Location"
                    value={""}
                    onChange={(e) => {
                      deleteFolders(e)
                    }}
                    helperText="Select folder to delete"
                    fullWidth
                >
                  {folders.map((option, index) => (
                      <MenuItem key={index} value={option}>
                        {option.name}
                      </MenuItem>
                  ))}
                </TextField>
              </ListItem>
          }
          <Divider/>

          <ListItem>
            <ListItemText>
              <Typography>Tags</Typography>
            </ListItemText>
          </ListItem>
          <ListItem>
            <ToggleButtonGroup
                color="primary"
                value={selectedTagGroup}
                exclusive
                onChange={(e) => handleChange(e, setSelectedTagGroup)}
                fullWidth
            >
              <ToggleButton value="add">Add</ToggleButton>
              <ToggleButton value="delete">Delete</ToggleButton>
            </ToggleButtonGroup>
          </ListItem>


          {selectedTagGroup === "add" &&
              <ListItem>
                <TextField
                    fullWidth
                    label="Add tag"
                    value={tagToAdd}
                    onChange={(e) => {
                      handleChange(e, setTagToAdd)
                    }}
                >
                </TextField>
                <ListItemButton onClick={() => {
                  putTag()
                  setTagToAdd("")
                }}
                                alignItems={"center"}>
                  <TypographyCenter>
                    Add
                  </TypographyCenter>
                </ListItemButton>
              </ListItem>
          }

          {selectedTagGroup === "delete" &&
              <ListItem>
                <TextField
                    select
                    value={''}
                    label={"Tags"}
                    fullWidth
                >
                  {possibleFilters.tags.map((value, index) => (
                      <ListItemButton key={index} value={value}
                                      onClick={() => deleteTags(value)}>
                        <ListItemText primary={value}/>
                        <DeleteIcon/>
                      </ListItemButton>
                  ))}
                </TextField>
              </ListItem>
          }


          <Divider/>

          <ListItemButton onClick={(e) => reIndex(e)}>
            <TypographyCenter>
              Re-Index
            </TypographyCenter>
          </ListItemButton>

          <Divider/>


          <ListItemButton
              onClick={(e) => setShowSettingsDrawer(!showSettingsDrawer)}>
            <TypographyCenter>
              <KeyboardArrowLeftIcon/>
            </TypographyCenter>
          </ListItemButton>

        </GenericDrawer>

        <GenericDrawer open={showFiltersDrawer}>

          <Divider/>
          <TitleListItem>
            <ListItemText>
              <Typography>Location</Typography>
            </ListItemText>
          </TitleListItem>


          <ListItem>
            <TextField
                select
                label="Location"
                value={""}
                onChange={(e) => {
                  if (selectedFilters.location.find(
                      f => f.name === e.target.value)) {
                    setSelectedFilters({
                      ...selectedFilters,
                      location: selectedFilters.location.filter(
                          f => f.name !== e.target.value)
                    })
                  } else {
                    setSelectedFilters({
                      ...selectedFilters,
                      location: [...selectedFilters.location,
                        folders.find(f => f.name === e.target.value)]
                    })
                  }
                }}
                helperText="Which folder to take images from"
                fullWidth
            >
              {folders.map((option, index) => (
                  <MenuItem key={index} value={option.name}>
                    <Checkbox
                        checked={selectedFilters.location.map(
                            f => f.name).includes(
                            option.name)}/>
                    <Typography>
                      {option.name}
                    </Typography>
                  </MenuItem>
              ))}
            </TextField>
          </ListItem>

          <ListItem>
            <TextField
                select
                label="Tags"
                value={""}
                onChange={(e) => {
                  if (selectedFilters.tags.find(
                      f => f === e.target.value)) {
                    setSelectedFilters({
                      ...selectedFilters,
                      tags: selectedFilters.tags.filter(
                          f => f !== e.target.value)
                    })
                  } else {
                    setSelectedFilters({
                      ...selectedFilters,
                      tags: [...selectedFilters.tags,
                        possibleFilters.tags.find(f => f === e.target.value)]
                    })
                  }
                }}
                fullWidth
            >
              {possibleFilters.tags.map((option, index) => (
                  <MenuItem key={index} value={option}>
                    <Checkbox
                        checked={selectedFilters.tags.includes(
                            option)}/>
                    <Typography>
                      {option}
                    </Typography>
                  </MenuItem>
              ))}
            </TextField>
          </ListItem>

          <ListItem>
            <ListItemText>
              <Typography>Sample steps: {Math.min(
                  ...selectedFilters.steps)}-{Math.max(
                  ...selectedFilters.steps)}</Typography>
            </ListItemText>
          </ListItem>
          <ListItem>
            <Slider value={selectedFilters.steps}
                    valueLabelDisplay="off"
                    onChange={(e) => setSelectedFilters(
                        {...selectedFilters, steps: e.target.value})}
                    min={Math.min(...possibleFilters.steps)}
                    max={Math.max(...possibleFilters.steps)}
                    step={5}
                    marks/>
          </ListItem>

          <ListItem>
            <TextField
                select
                label="Sampler"
                value={""}
                onChange={(e) => {
                  if (selectedFilters.sampler.find(
                      f => f === e.target.value)) {
                    setSelectedFilters({
                      ...selectedFilters,
                      sampler: selectedFilters.sampler.filter(
                          f => f !== e.target.value)
                    })
                  } else {
                    setSelectedFilters({
                      ...selectedFilters,
                      sampler: [...selectedFilters.sampler,
                        possibleFilters.sampler.find(f => f === e.target.value)]
                    })
                  }
                }}
                fullWidth
            >
              {possibleFilters.sampler.map((option, index) => (
                  <MenuItem key={index} value={option}>
                    <Checkbox
                        checked={selectedFilters.sampler.includes(
                            option)}/>
                    <Typography>
                      {option}
                    </Typography>
                  </MenuItem>
              ))}
            </TextField>
          </ListItem>

          <ListItem>
            <ListItemText>
              <Typography>Denoise: {Math.min(
                  ...selectedFilters.denoise)}-{Math.max(
                  ...selectedFilters.denoise)}</Typography>
            </ListItemText>
          </ListItem>
          <ListItem>
            <Slider value={selectedFilters.denoise}
                    valueLabelDisplay="off"
                    onChange={(e) => setSelectedFilters(
                        {...selectedFilters, denoise: e.target.value})}
                    min={Math.min(...possibleFilters.denoise)}
                    max={Math.max(...possibleFilters.denoise)}
                    step={0.01}
                    marks/>
          </ListItem>

          <ListItem>
            <ListItemText>
              <Typography>CFG: {Math.min(
                  ...selectedFilters.cfg)}-{Math.max(
                  ...selectedFilters.cfg)}</Typography>
            </ListItemText>
          </ListItem>
          <ListItem>
            <Slider value={selectedFilters.cfg}
                    valueLabelDisplay="off"
                    onChange={(e) => setSelectedFilters(
                        {...selectedFilters, cfg: e.target.value})}
                    min={Math.min(...possibleFilters.cfg)}
                    max={Math.max(...possibleFilters.cfg)}
                    step={0.5}
                    marks/>
          </ListItem>

          <ListItem>
            <TextField
                select
                label="Model hash"
                value={""}
                onChange={(e) => {
                  if (selectedFilters.modelHash.find(
                      f => f === e.target.value)) {
                    setSelectedFilters({
                      ...selectedFilters,
                      modelHash: selectedFilters.modelHash.filter(
                          f => f !== e.target.value)
                    })
                  } else {
                    setSelectedFilters({
                      ...selectedFilters,
                      modelHash: [...selectedFilters.modelHash,
                        possibleFilters.modelHash.find(
                            f => f === e.target.value)]
                    })
                  }
                }}
                fullWidth
            >
              {possibleFilters.modelHash.map((option, index) => (
                  <MenuItem key={index} value={option}>
                    <Checkbox
                        checked={selectedFilters.modelHash.includes(
                            option)}/>
                    <Typography>
                      {option}
                    </Typography>
                  </MenuItem>
              ))}
            </TextField>
          </ListItem>

          <ListItem>
            <TextField
                select
                label="Face restoration"
                value={""}
                onChange={(e) => {
                  if (selectedFilters.faceRestoration.find(
                      f => f === e.target.value)) {
                    setSelectedFilters({
                      ...selectedFilters,
                      faceRestoration: selectedFilters.faceRestoration.filter(
                          f => f !== e.target.value)
                    })
                  } else {
                    setSelectedFilters({
                      ...selectedFilters,
                      faceRestoration: [...selectedFilters.faceRestoration,
                        possibleFilters.faceRestoration.find(
                            f => f === e.target.value)]
                    })
                  }
                }}
                fullWidth
            >
              {possibleFilters.faceRestoration.map((option, index) => (
                  <MenuItem key={index} value={option}>
                    <Checkbox
                        checked={selectedFilters.faceRestoration.includes(
                            option)}/>
                    <Typography>
                      {option}
                    </Typography>
                  </MenuItem>
              ))}
            </TextField>
          </ListItem>

          <ListItem>
            <TextField
                select
                label="Hypernet"
                value={""}
                onChange={(e) => {
                  if (selectedFilters.hypernet.find(
                      f => f === e.target.value)) {
                    setSelectedFilters({
                      ...selectedFilters,
                      hypernet: selectedFilters.hypernet.filter(
                          f => f !== e.target.value)
                    })
                  } else {
                    setSelectedFilters({
                      ...selectedFilters,
                      hypernet: [...selectedFilters.hypernet,
                        possibleFilters.hypernet.find(
                            f => f === e.target.value)]
                    })
                  }
                }}
                fullWidth
            >
              {possibleFilters.hypernet.map((option, index) => (
                  <MenuItem key={index} value={option}>
                    <Checkbox
                        checked={selectedFilters.hypernet.includes(
                            option)}/>
                    <Typography>
                      {option}
                    </Typography>
                  </MenuItem>
              ))}
            </TextField>
          </ListItem>

          <ListItem>
            <ListItemText>
              <Typography>Clip skip: {Math.min(
                  ...selectedFilters.clipSkip)}-{Math.max(
                  ...selectedFilters.clipSkip)}</Typography>
            </ListItemText>
          </ListItem>
          <ListItem>
            <Slider value={selectedFilters.clipSkip}
                    valueLabelDisplay="off"
                    onChange={(e) => setSelectedFilters(
                        {...selectedFilters, clipSkip: e.target.value})}
                    min={Math.min(...possibleFilters.clipSkip)}
                    max={Math.max(...possibleFilters.clipSkip)}
                    step={1}
                    marks/>
          </ListItem>


          <ListItem>
            <ListItemText>
              <Typography>Width: {Math.min(
                  ...selectedFilters.width)}-{Math.max(
                  ...selectedFilters.width)}</Typography>
            </ListItemText>
          </ListItem>
          <ListItem>
            <Slider value={selectedFilters.width}
                    valueLabelDisplay="off"
                    onChange={(e) => setSelectedFilters(
                        {...selectedFilters, width: e.target.value})}
                    min={Math.min(...possibleFilters.width)}
                    max={Math.max(...possibleFilters.width)}
                    step={64}
                    marks/>
          </ListItem>

          <ListItem>
            <ListItemText>
              <Typography>Height: {Math.min(
                  ...selectedFilters.height)}-{Math.max(
                  ...selectedFilters.height)}</Typography>
            </ListItemText>
          </ListItem>
          <ListItem>
            <Slider value={selectedFilters.height}
                    valueLabelDisplay="off"
                    onChange={(e) => setSelectedFilters(
                        {...selectedFilters, height: e.target.value})}
                    min={Math.min(...possibleFilters.height)}
                    max={Math.max(...possibleFilters.height)}
                    step={64}
                    marks/>
          </ListItem>

          <ListItem>
            <TextField
                fullWidth
                id="datetime-local"
                label="Images after"
                type="datetime-local"
                value={selectedFilters.afterDate}
                onChange={(e) => setSelectedFilters(
                    {...selectedFilters, afterDate: e.target.value})}
            />
          </ListItem>

          <ListItem>
            <TextField
                fullWidth
                id="datetime-local"
                label="Images before"
                type="datetime-local"
                value={selectedFilters.beforeDate}
                onChange={(e) => setSelectedFilters(
                    {...selectedFilters, beforeDate: e.target.value})}
            />
          </ListItem>


          <Divider/>

          <ListItemButton
              onClick={(e) => getImages()}>
            <TypographyCenter>
              Filter
            </TypographyCenter>
          </ListItemButton>
          <Divider/>
          <ListItemButton
              onClick={(e) => setShowFiltersDrawer(!showFiltersDrawer)}>
            <TypographyCenter>
              <KeyboardArrowLeftIcon/>
            </TypographyCenter>
          </ListItemButton>

        </GenericDrawer>

        <FilterFab fromBot={0}
                   fromLeft={0}
                   onClick={(e) => setShowSettingsDrawer(!showSettingsDrawer)}
                   size={"medium"}>
          <SettingsIcon/>
        </FilterFab>

        <FilterFab fromBot={1}
                   fromLeft={0}
                   onClick={(e) => setShowFiltersDrawer(!showFiltersDrawer)}
                   size={"medium"}>
          <FilterListIcon/>
        </FilterFab>

        {images.length > 0 && <FilterFab fromBot={0}
                                         fromLeft={1}
                                         onClick={(e) => selectedImages.length
                                         === images.length
                                             ? setSelectedImages([])
                                             : setSelectedImages(images)}
                                         size={"medium"}>
          <Checkbox checked={selectedImages.length === images.length}/>
        </FilterFab>}


        {selectedImages.length > 0 && <FilterFab fromBot={0}
                                                 fromLeft={2}
                                                 variant={"extended"}>
          <TextField
              select
              size={"small"}
              value={""}
              InputProps={{
                startAdornment: (
                    <InputAdornment position={"start"}>
                      <FolderIcon color={"primary"}/>
                    </InputAdornment>
                )
              }}
              onChange={(e) => putImages(e)}>
            {folders.map((option, index) => (
                <MenuItem key={index} value={option.path}>
                  <Typography>
                    {option.name}
                  </Typography>
                </MenuItem>
            ))}
          </TextField>

        </FilterFab>}


        {showImage && (
            <Dialog fullWidth maxWidth={false} open={showImage}
                    onClose={() => setShowImage(!showImage)}>
              <DialogNoPadding>
                <Grid
                    container
                    direction="row"
                    justifyContent="space-between"
                    alignItems="center"
                >
                  <CenterMaxHeightGrid item xs={6}>
                    <GridMaxHeight container
                                   direction="row"
                                   justifyContent="center"
                                   alignItems="center">
                      <CenterGrid item xs={scale}>
                        <Image
                            id={imageToDisplay.fileName}
                            src={`${process.env.LOCALHOST8080}/image?url=${imageToDisplay.location.replaceAll(
                                "\\", "/")}/${imageToDisplay.fileName}`}
                            alt=""
                            layout="responsive"
                            width={imageToDisplay.width}
                            height={imageToDisplay.height}
                            loading="lazy"
                        />
                      </CenterGrid>
                    </GridMaxHeight>
                  </CenterMaxHeightGrid>
                  <CenterGrid item xs={6}>
                    <Grid item>
                      <Typography>Zoom</Typography>
                    </Grid>
                    <GridMargin container
                                direction="row"
                                justifyContent="center"
                                alignItems="center">
                      <Grid item>
                        <IconButton onClick={e => {
                          setScale(scale - 0.1)
                        }}>
                          <RemoveCircleOutlineIcon/>
                        </IconButton>
                      </Grid>
                      <Grid item>
                        <IconButton onClick={e => {
                          setScale(scale + 0.1)
                        }}>
                          <AddCircleOutlineIcon/>
                        </IconButton>
                      </Grid>
                    </GridMargin>


                    <Grid item>
                      <Typography>Prompts</Typography>
                    </Grid>
                    <Grid item>
                      <TextFieldMargin label={"Positive prompts"} fullWidth
                                       multiline
                                       value={imageToDisplay.positivePrompt
                                       !== null
                                           ? imageToDisplay.positivePrompt
                                           : "None indexed"}/>
                    </Grid>

                    <Grid item>
                      <TextFieldMargin label={"Negative prompts"} fullWidth
                                       multiline
                                       value={imageToDisplay.negativePrompt
                                       !== null
                                           ? imageToDisplay.negativePrompt
                                           : "None indexed"}/>
                    </Grid>

                    <Grid item>
                      <TextFieldMargin label={"Generation Information"}
                                       fullWidth multiline
                                       value={imageToDisplay !== null
                                           ? Object.entries(
                                               imageToDisplay)
                                           .filter(
                                               k =>
                                                   k[0] !== "positivePrompt"
                                                   && k[0]
                                                   !== "negativePrompt"
                                                   && k[0]
                                                   !== "_id"
                                                   && k[0]
                                                   !== "location"
                                                   && k[0]
                                                   !== "fileName"
                                                   && k[0]
                                                   !== "creationDate"
                                                   && k[0]
                                                   !== "tags")// TODO Refactor to make it not look like it was written by a 5 year old
                                           .map((k, i) => {
                                             const space = i !== 0 ? " " : ""
                                             return space + k[0] + ": " + k[1];

                                           }) : "None indexed"}/>
                    </Grid>

                    <Grid item>
                      <Typography>
                        Tags
                      </Typography>
                    </Grid>

                    <Grid container
                          direction="row"
                          justifyContent="center"
                          alignItems="center">
                      {imageToDisplay && imageToDisplay.tags.sort().map(
                          (data, index) =>
                              <Grid item key={index}>
                                <ListItem>
                                  <Chip
                                      label={data}
                                  />
                                </ListItem>
                              </Grid>
                      )}
                    </Grid>
                  </CenterGrid>
                </Grid>
              </DialogNoPadding>
            </Dialog>
        )}

      </StyledBox>
  );
};

export default ImageViewer;