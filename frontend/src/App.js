import React, { useEffect, useMemo, useState } from 'react';
import MaterialReactTable from 'material-react-table';
import { Box, Button } from '@mui/material';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import {Routes, Route, useNavigate} from 'react-router-dom';
import { ExportToCsv } from 'export-to-csv'; //or use your library of choice here
import ReactDOM from "react-dom";
import { useAuth0, Auth0Provider } from "@auth0/auth0-react";
import LoginButton from "./components/LoginButton";
import LogOutButton from "./components/LogOutButton";

const columns = [
  //{
  //  accessorKey: 'id',
  //  header: 'ID',
  //  size: 40,
  //},
  {
    accessorKey: 'title',
    header: 'Title',
    size: 120,
  },
  {
    accessorKey: 'creatorName',
    header: 'Name',
    size: 120,
  },
  {
    accessorKey: 'creatorJob',
    header: 'Job',
    size: 120,
  },
  {
    accessorKey: 'main_characters',
    header: 'Main character',
    size: 300,
  },
  {
    accessorKey: 'side_character',
    header: 'Side character',
  },
  {
    accessorKey: 'price',
    header: 'Price',
    size: 220,
  },
  {
    accessorKey: 'release_date',
    header: 'Release date',
    size: 120,
  },
  {
    accessorKey: 'publisher',
    header: 'Publisher',
    size: 120,
  },
  {
    accessorKey: 'page_count',
    header: 'Page count',
    size: 300,
  },
  {
    accessorKey: 'format',
    header: 'Format',
  },
  {
    accessorKey: 'issue_number',
    header: 'Issue number',
    size: 220,
  },
  {
    accessorKey: 'rating',
    header: 'Rating',
    size: 220,
  },
];

const csvOptions = {
  fieldSeparator: ',',
  filename: 'ComicsCsv',
  quoteStrings: '"',
  decimalSeparator: '.',
  showLabels: true,
  useBom: true,
  useKeysAsHeaders: false,
  headers: columns.map((c) => c.header),
};


const csvExporter = new ExportToCsv(csvOptions);

const Example = () => {

  const [data, setUser] = useState([]);

  const { loginWithRedirect, isAuthenticated} = useAuth0();
  const { logout } = useAuth0();
  const { user } = useAuth0();

  const fetchData = () => {
    return fetch("/api/getAll")
        .then((response) => response.json())
        .then((data) => setUser(data));
  }

  useEffect(() => {
    fetchData();
  },[])

  setInterval(function(){
    fetchData()
  }, 30000)

  const handleExportRows = (rows) => {
    csvExporter.generateCsv(rows.map((row) => row.original));
  };
  const handleExportJson = (rows) =>{
     const obj = rows.map((row) => row.original);
     const jsonobj = `data:text/json;chatset=utf-8,${encodeURIComponent(
         JSON.stringify(obj)
     )}`;
     const link = document.createElement("a");
     link.href = jsonobj;
     link.download = "data.json";
     link.click();
  };

  function refreshData(data1){
    handleExportJson(data1);
    handleExportData(data);
  }

  const LoginButton = () => {
    loginWithRedirect();
  };
  const LogOutButton = () => {
    logout();
  };
  const UserInfo = () => {
    var ispis = "Email : " + user.email + "\nUsername : " + user.name + "\nNickname : " + user.nickname;
    alert(ispis);
  };

  const handleLogRows = (rows) => {
    console.log(rows.map((row) => console.log(row.original)));
    console.log((rows.map((row) => JSON.stringify(row.original).replace("Object ", ""))));

  };
  const handleExportData = () => {
    csvExporter.generateCsv(data);
  };
  if (isAuthenticated) {
    return (
        <main>
          <MaterialReactTable
              columns={columns}
              data={data}
              enableRowSelection
              positionToolbarAlertBanner="bottom"
              renderTopToolbarCustomActions={({table}) => (
                  <Box
                      sx={{display: 'flex', gap: '1rem', p: '0.5rem', flexWrap: 'wrap'}}
                  >
                    <Button
                        color="primary"
                        //export all data that is currently in the table (ignore pagination, sorting, filtering, etc.)
                        onClick={handleExportData}
                        startIcon={<FileDownloadIcon/>}
                        variant="contained"
                    >
                      Export All Data
                    </Button>
                    <Button
                        disabled={table.getPrePaginationRowModel().rows.length === 0}
                        //export all rows, including from the next page, (still respects filtering and sorting)
                        onClick={() =>
                            handleExportRows(table.getPrePaginationRowModel().rows)
                        }
                        startIcon={<FileDownloadIcon/>}
                        variant="contained"
                    >
                      Export All Rows
                    </Button>
                    <Button
                        disabled={table.getRowModel().rows.length === 0}
                        //export all rows as seen on the screen (respects pagination, sorting, filtering, etc.)
                        onClick={() => handleExportRows(table.getRowModel().rows)}
                        startIcon={<FileDownloadIcon/>}
                        variant="contained"
                    >
                      Export Page Rows
                    </Button>
                    <Button
                        disabled={
                            !table.getIsSomeRowsSelected() && !table.getIsAllRowsSelected()
                        }
                        //only export selected rows
                        onClick={() => handleExportRows(table.getSelectedRowModel().rows)}
                        startIcon={<FileDownloadIcon/>}
                        variant="contained"
                    >
                      Export Selected Rows
                    </Button>
                    <Button
                        disabled={table.getPrePaginationRowModel().rows.length === 0}
                        //export all rows, including from the next page, (still respects filtering and sorting)
                        onClick={() =>
                            handleExportJson(table.getPrePaginationRowModel().rows)
                        }
                        startIcon={<FileDownloadIcon/>}
                        variant="contained"
                    >
                      Export JSON
                    </Button>
                    <Button
                        //export all rows, including from the next page, (still respects filtering and sorting)
                        onClick={() =>
                            LogOutButton()
                        }
                        variant="contained"
                    >
                      Logout
                    </Button>
                    <Button
                        //export all rows, including from the next page, (still respects filtering and sorting)
                        onClick={() => refreshData(table.getPrePaginationRowModel().rows)}
                        startIcon={<FileDownloadIcon/>}
                        variant="contained"
                    >
                      Osvježi preslike
                    </Button>
                    <Button
                        //export all rows, including from the next page, (still respects filtering and sorting)
                        onClick={() =>
                            UserInfo()
                        }
                        variant="contained"
                    >
                      Korisnički profil
                    </Button>
                  </Box>
              )}
          />
        </main>
    );
  } else {
    return (
        <main>
          <MaterialReactTable
              columns={columns}
              data={data}
              enableRowSelection
              positionToolbarAlertBanner="bottom"
              renderTopToolbarCustomActions={({table}) => (
                  <Box
                      sx={{display: 'flex', gap: '1rem', p: '0.5rem', flexWrap: 'wrap'}}
                  >
                    <Button
                        color="primary"
                        //export all data that is currently in the table (ignore pagination, sorting, filtering, etc.)
                        onClick={handleExportData}
                        startIcon={<FileDownloadIcon/>}
                        variant="contained"
                    >
                      Export All Data
                    </Button>
                    <Button
                        disabled={table.getPrePaginationRowModel().rows.length === 0}
                        //export all rows, including from the next page, (still respects filtering and sorting)
                        onClick={() =>
                            handleExportRows(table.getPrePaginationRowModel().rows)
                        }
                        startIcon={<FileDownloadIcon/>}
                        variant="contained"
                    >
                      Export All Rows
                    </Button>
                    <Button
                        disabled={table.getRowModel().rows.length === 0}
                        //export all rows as seen on the screen (respects pagination, sorting, filtering, etc.)
                        onClick={() => handleExportRows(table.getRowModel().rows)}
                        startIcon={<FileDownloadIcon/>}
                        variant="contained"
                    >
                      Export Page Rows
                    </Button>
                    <Button
                        disabled={
                            !table.getIsSomeRowsSelected() && !table.getIsAllRowsSelected()
                        }
                        //only export selected rows
                        onClick={() => handleExportRows(table.getSelectedRowModel().rows)}
                        startIcon={<FileDownloadIcon/>}
                        variant="contained"
                    >
                      Export Selected Rows
                    </Button>
                    <Button
                        disabled={table.getPrePaginationRowModel().rows.length === 0}
                        //export all rows, including from the next page, (still respects filtering and sorting)
                        onClick={() =>
                            handleExportJson(table.getPrePaginationRowModel().rows)
                        }
                        startIcon={<FileDownloadIcon/>}
                        variant="contained"
                    >
                      Export JSON
                    </Button>
                    <Button
                      //export all rows, including from the next page, (still respects filtering and sorting)
                      onClick={() =>
                          LoginButton()
                      }
                      variant="contained"
                  >
                    Login
                  </Button>
                  </Box>
              )}
          />
        </main>
    );
  }
};

export default Example;