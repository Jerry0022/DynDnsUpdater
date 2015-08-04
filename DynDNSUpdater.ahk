#SingleInstance force
#NoEnv  ; Recommended for performance and compatibility with future AutoHotkey releases.
SendMode Input  ; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.

Loop {
	RunWait, DynDNSUpdater.jar
	Sleep, 60*1000 ; every 1 minutes
}
