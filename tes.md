# Tubes2_dicarryVieridanZaki
<h2 align="center">
📱 BFS and DFS Algorithms for Solving Maze Treasure Hunt Problems 📱<br/>
</h2>
<hr>

## Table of Contents
1. [General Info](#general-information)
2. [Creator Info](#creator-information)
3. [Features](#features)
4. [Technologies Used](#technologies-used)
5. [Setup](#setup)
6. [Usage](#usage)
7. [Video Capture](#videocapture)
8. [Screenshots](#screenshots)
9. [Structure](#structure)
10. [Project Status](#project-status)
11. [Room for Improvement](#room-for-improvement)
12. [Acknowledgements](#acknowledgements)
13. [Contact](#contact)

<a name="general-information"></a>

## General Information
A simple program that write in C# language to implement BFS and DFS algorithm for solving maze treasure hunt problems. This project was written in C# programming language using Visual Studio and Windows Presentation Foundation (WPF) for the GUI. Treasure Hunter App will read the input from a text file and then display the result in the GUI. The program will also display the number of nodes that have been visited and the number of nodes that have been expanded. Besides that, this project also has a feature to display the execution time of the program. This project made for fulfilling Tubes 2 of IF2211 - Strategi Algoritma course at ITB 2022/2023.

<a name="creator-information"></a>

## Creator Information

| Nama                        | NIM      | E-Mail                      |
| --------------------------- | -------- | --------------------------- |
| Vieri Fajar Firdaus         | 13521099 | 13521099@std.stei.itb.ac.id |
| Muhammad Zaki Amanullah     | 13521146 | 13521146@std.stei.itb.ac.id |
| Mohammad Rifqi Farhansyah   | 13521166 | 13521166@std.stei.itb.ac.id |

<a name="features"></a>

## Features
- Read `input` from a text file (via file explorer or textfield)
- User can choose `the algorithm` to be used (BFS or DFS)
- There is a additional feature to display the `TSP` solution of the problem
- Customize the `search speed` of the algorithm
- Show the `maze` in the GUI
- Visualize the `path` from the start to the end
- Display the `route`, `number of nodes visited`, `number of nodes expanded`, and `execution time` in the GUI

<a name="technologies-used"></a>

## Technologies Used
- Microsoft Visual Studio 2022 - version 17.4.3
- Microsoft .NET Framework - version 4.8.04084
- C# Tools - version 4.4.0

> Note: The version of the libraries above is the version that we used in this project. You can use the latest version of the libraries.

<a name="setup"></a>

## Setup
1. Download the latest version of Microsoft Visual Studio 2022 [here](https://visualstudio.microsoft.com/downloads/)
2. Download the latest version of Microsoft .NET Framework [here](https://dotnet.microsoft.com/download/dotnet-framework)
3. Download the latest version of C# Tools [here](https://marketplace.visualstudio.com/items?itemName=ms-vscode.csharp)
4. Install all the tools above
5. Clone this repository to your local directory by using this command in your terminal
```bash
git clone https://github.com/rifqifarhansyah/Tubes2_dicarryVieridanZaki.git
```

<a name="usage"></a>

## Usage
1. Open the project in Visual Studio
2. To open to the project, you can use the following steps:
    - Click `File` in the top left corner
    - Click `Open` > `Project/Solution`
    - Navigate to the directory where you clone this repository
    - Go to `Tubes2_dicarryVieridanZaki/src/TreasureHunterApp` folder
    - Click `TreasureHunterApp.csproj`
3. After the project was opened:
    - right click on `TreasureHunterApp` in the `Solution Explorer` on the left side of the screen
    - Choose `Publish`
    - Browse the directory where you want to save the executable file
    - Click `Finish`
    - The executable file will be saved in the directory that you choose
    - You can run the executable file by double clicking `setup.exe`
    - And then click `install` to install the program

<a name="videocapture"></a>

## Video Capture
<nl>

![Treasure Hunt Gif](https://github.com/rifqifarhansyah/Tubes2_dicarryVieridanZaki/blob/main/img/TreasureHunter.gif?raw=true)

<a name="screenshots"></a>

## Screenshots
<p>
  <img src="/img/SS1.png/">
  <p>Figure 1. Config File (*txt)</p>
  <nl>
  <img src="/img/SS2.png/">
  <p>Figure 2. Initial Appearance of the Program</p>
  <nl>
  <img src="/img/SS3.png/">
  <p>Figure 3. Result</p>
  <nl>
</p>

<a name="structure"></a>

## Structure
```bash
│   .gitignore
│   README.md
│
├───.vs
│   │   slnx.sqlite
│   │   tasks.vs.json
│   │   VSWorkspaceState.json
│   │
│   ├───Tubes2_13521099
│   │   ├───FileContentIndex
│   │   │       1f6478ed-6d4c-41fc-8310-5fee686d57d6.vsidx
│   │   │       ac8a0297-178a-4a4d-a8d1-07438962f32a.vsidx
│   │   │       e43822dc-b10f-4363-b8c2-785d1c86a63c.vsidx
│   │   │       e6333f6e-1a45-47ad-901e-a8e062fddfcc.vsidx
│   │   │       read.lock
│   │   │
│   │   └───v17
│   │       │   .wsuo
│   │       │
│   │       └───TestStore
│   │           └───0
│   │                   000.testlog
│   │                   testlog.manifest
│   │
│   └───Tubes2_dicarryVieridanZaki
│       ├───config
│       │       applicationhost.config
│       │
│       ├───FileContentIndex
│       │   │   25841f9c-98ef-495e-a3cb-b4ce8cc36050.vsidx
│       │   │   2ad4844b-b28a-42d7-b454-977c672cfadc.vsidx
│       │   │   b50e6171-5418-41ac-977a-a5e3990006a9.vsidx
│       │   │   c772a3ef-946f-4640-9d12-9e40acf8ffc8.vsidx
│       │   │   read.lock
│       │   │
│       │   └───merges
│       └───v17
├───bin
│   │   autorun.inf
│   │   setup.exe
│   │   TreasureHunterApp.application
│   │
│   └───Application Files
│       └───TreasureHunterApp_1_0_0_11
│               icons8-treasure-chest-80.png.deploy
│               Money.png.deploy
│               Treasure.png.deploy
│               TreasureHunterApp.application
│               TreasureHunterApp.exe.config.deploy
│               TreasureHunterApp.exe.deploy
│               TreasureHunterApp.exe.manifest
│
├───doc
│       dicarryVieridanZaki.pdf
│       Tubes2-Stima-2023_230302_144558.pdf
│
├───img
│       icons8-treasure-chest-80.png
│       Money.png
│       SS1.png
│       SS2.png
│       SS3.png
│       Treasure.png
│       TreasureHunter.gif
│
├───src
│   └───TreasureHunterApp
│       │   App.config
│       │   App.xaml
│       │   App.xaml.cs
│       │   BackendDebugger.cs
│       │   BFS.cs
│       │   ColorExtension.cs
│       │   DFS.cs
│       │   icons8-treasure-chest-80.png
│       │   MainWindow.xaml
│       │   MainWindow.xaml.cs
│       │   Maze.cs
│       │   microsoft.windowsapicodepack-shell.1.1.0.nupkg
│       │   Money.png
│       │   Node.cs
│       │   SolidColorBrushComparer.cs
│       │   Treasure.png
│       │   TreasureHunterApp.csproj
│       │   TreasureHunterApp.csproj.user
│       │   TreasureHunterApp.sln
│       │   TreasureHunterApp_TemporaryKey.pfx
│       │   treasurehuntericon.png
│       │
│       ├───.vs
│       │   └───TreasureHunterApp
│       │       ├───FileContentIndex
│       │       │       2f4b00c2-4538-4ab2-8c93-2f2445939028.vsidx
│       │       │       736a53da-437b-42a0-8efc-fb31cacc1ded.vsidx
│       │       │       80a182a5-ff69-4f0a-a280-87b00e8ba85a.vsidx
│       │       │       9cae0026-9ed7-4024-8ed0-824770039fcd.vsidx
│       │       │       ee195175-79b1-47f1-a0e8-3ebb2e71b578.vsidx
│       │       │       read.lock
│       │       │
│       │       └───v17
│       │               .suo
│       │
│       ├───bin
│       │   ├───Debug
│       │   │   │   icons8-treasure-chest-80.png
│       │   │   │   Money.png
│       │   │   │   Treasure.png
│       │   │   │   TreasureHunterApp.application
│       │   │   │   TreasureHunterApp.exe
│       │   │   │   TreasureHunterApp.exe.config
│       │   │   │   TreasureHunterApp.exe.manifest
│       │   │   │   TreasureHunterApp.pdb
│       │   │   │
│       │   │   ├───app.publish
│       │   │   │       TreasureHunterApp.exe
│       │   │   │
│       │   │   └───img
│       │   │           icons8-treasure-chest-80.png
│       │   │           Money.png
│       │   │           Treasure.png
│       │   │
│       │   └───Release
│       │       │   icons8-treasure-chest-80.png
│       │       │   Money.png
│       │       │   Treasure.png
│       │       │   TreasureHunterApp.application
│       │       │   TreasureHunterApp.exe
│       │       │   TreasureHunterApp.exe.config
│       │       │   TreasureHunterApp.exe.manifest
│       │       │   TreasureHunterApp.pdb
│       │       │
│       │       ├───app.publish
│       │       │       TreasureHunterApp.exe
│       │       │
│       │       └───img
│       │               icons8-treasure-chest-80.png
│       │               Money.png
│       │               Treasure.png
│       │
│       ├───obj
│       │   ├───Debug
│       │   │   │   .NETFramework,Version=v4.7.2.AssemblyAttributes.cs
│       │   │   │   App.g.cs
│       │   │   │   App.g.i.cs
│       │   │   │   DesignTimeResolveAssemblyReferences.cache
│       │   │   │   DesignTimeResolveAssemblyReferencesInput.cache
│       │   │   │   MainWindow.baml
│       │   │   │   MainWindow.g.cs
│       │   │   │   MainWindow.g.i.cs
│       │   │   │   TreasureHunterApp.application
│       │   │   │   TreasureHunterApp.csproj.AssemblyReference.cache
│       │   │   │   TreasureHunterApp.csproj.CoreCompileInputs.cache
│       │   │   │   TreasureHunterApp.csproj.FileListAbsolute.txt
│       │   │   │   TreasureHunterApp.csproj.GenerateResource.cache
│       │   │   │   TreasureHunterApp.csproj.SuggestedBindingRedirects.cache
│       │   │   │   TreasureHunterApp.exe
│       │   │   │   TreasureHunterApp.exe.manifest
│       │   │   │   TreasureHunterApp.g.resources
│       │   │   │   TreasureHunterApp.pdb
│       │   │   │   TreasureHunterApp.Properties.Resources.resources
│       │   │   │   TreasureHunterApp_al4xpzfm_wpftmp.csproj.AssemblyReference.cache
│       │   │   │   TreasureHunterApp_Content.g.cs
│       │   │   │   TreasureHunterApp_Content.g.i.cs
│       │   │   │   TreasureHunterApp_MarkupCompile.cache
│       │   │   │   TreasureHunterApp_MarkupCompile.i.cache
│       │   │   │   TreasureHunterApp_MarkupCompile.lref
│       │   │   │
│       │   │   └───TempPE
│       │   │           KrustyKrab.Designer.cs.dll
│       │   │           Pics.Designer.cs.dll
│       │   │           Properties.Resources.Designer.cs.dll
│       │   │
│       │   └───Release
│       │       │   .NETFramework,Version=v4.7.2.AssemblyAttributes.cs
│       │       │   App.g.cs
│       │       │   App.g.i.cs
│       │       │   DesignTimeResolveAssemblyReferences.cache
│       │       │   DesignTimeResolveAssemblyReferencesInput.cache
│       │       │   MainWindow.g.cs
│       │       │   MainWindow.g.i.cs
│       │       │   TreasureHunterApp.application
│       │       │   TreasureHunterApp.csproj.AssemblyReference.cache
│       │       │   TreasureHunterApp.csproj.CoreCompileInputs.cache
│       │       │   TreasureHunterApp.csproj.FileListAbsolute.txt
│       │       │   TreasureHunterApp.csproj.GenerateResource.cache
│       │       │   TreasureHunterApp.csproj.SuggestedBindingRedirects.cache
│       │       │   TreasureHunterApp.exe
│       │       │   TreasureHunterApp.exe.manifest
│       │       │   TreasureHunterApp.g.resources
│       │       │   TreasureHunterApp.pdb
│       │       │   TreasureHunterApp.Properties.Resources.resources
│       │       │   TreasureHunterApp_al4xpzfm_wpftmp.csproj.AssemblyReference.cache
│       │       │   TreasureHunterApp_Content.g.cs
│       │       │   TreasureHunterApp_Content.g.i.cs
│       │       │   TreasureHunterApp_MarkupCompile.cache
│       │       │   TreasureHunterApp_MarkupCompile.i.cache
│       │       │   TreasureHunterApp_MarkupCompile.lref
│       │       │
│       │       └───TempPE
│       │               KrustyKrab.Designer.cs.dll
│       │               Properties.Resources.Designer.cs.dll
│       │
│       └───Properties
│               AssemblyInfo.cs
│               Resources.Designer.cs
│               Resources.resx
│               Settings.Designer.cs
│               Settings.settings
│
└───test
        test.txt
        test.zip
        test1.txt
        test10.txt
        test11.txt
        test12.txt
        test13.txt
        test14.txt
        test15.txt
        test16.txt
        test2.txt
        test4.txt
        test5.txt
        test6.txt
        test7.txt
        test8.txt
        test9.txt
        TestMaze.txt
```

<a name="project-status">

## Project Status
Project is: _complete_

<a name="room-for-improvement">

## Room for Improvement
Room for Improvement:
- Improve the effectiveness of the BFS and DFS algorithm implementation
- Add more features

<a name="acknowledgements">

## Acknowledgements
- Thanks To Allah SWT

<a name="contact"></a>

## Contact
<h4 align="center">
  Contact Us : dicarryVieridanZaki<br/>
  2023
</h4>
<hr>
