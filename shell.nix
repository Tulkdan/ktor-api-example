{ pkgs ? import<nixpkgs> {} }:
let fhs = pkgs.buildFHSUserEnv {
  name = "gradle-env";
  targetPkgs = pkgs : (with pkgs;
  [
    gradle
    kotlin
    jdk
    zlib
    ncurses
    freetype
  ]);
};
in pkgs.stdenv.mkDerivation {
  name = "ktor-env-shell";
  nativeBuildInputs = [ fhs ];
  shellHook = ''
    export JAVA_HOME=${pkgs.jdk}
    PATH="${pkgs.jdk}/bin:$PATH"
  '';
}