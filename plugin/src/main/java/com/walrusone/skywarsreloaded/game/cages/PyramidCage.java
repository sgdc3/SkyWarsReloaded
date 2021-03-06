package com.walrusone.skywarsreloaded.game.cages;

import com.walrusone.skywarsreloaded.menus.gameoptions.objects.CoordLoc;

public class PyramidCage extends Cage {

	public PyramidCage() {
		cageType = CageType.PYRAMID;
		coordOffsets.add(new CoordLoc(0,0,0));
       coordOffsets.add(new CoordLoc(1,0,0));
       coordOffsets.add(new CoordLoc(1,0,1));
       coordOffsets.add(new CoordLoc(1,0,-1));
       coordOffsets.add(new CoordLoc(1,0,2));
       coordOffsets.add(new CoordLoc(1,0,-2));
       coordOffsets.add(new CoordLoc(2,0,0));
       coordOffsets.add(new CoordLoc(2,0,1));
       coordOffsets.add(new CoordLoc(2,0,-1));
       coordOffsets.add(new CoordLoc(3,0,0));
       coordOffsets.add(new CoordLoc(-1,0,0));
       coordOffsets.add(new CoordLoc(-1,0,1));
       coordOffsets.add(new CoordLoc(-1,0,-1));
       coordOffsets.add(new CoordLoc(-1,0,2));
       coordOffsets.add(new CoordLoc(-1,0,-2));
       coordOffsets.add(new CoordLoc(-2,0,0));
       coordOffsets.add(new CoordLoc(-2,0,1));
       coordOffsets.add(new CoordLoc(-2,0,-1));
       coordOffsets.add(new CoordLoc(-3,0,0));
       coordOffsets.add(new CoordLoc(0,0,1));
       coordOffsets.add(new CoordLoc(0,0,2));
       coordOffsets.add(new CoordLoc(0,0,3));
       coordOffsets.add(new CoordLoc(0,0,-1));
       coordOffsets.add(new CoordLoc(0,0,-2));
       coordOffsets.add(new CoordLoc(0,0,-3));
       
       coordOffsets.add(new CoordLoc(1,1,2));
       coordOffsets.add(new CoordLoc(1,1,-2));
       coordOffsets.add(new CoordLoc(2,1,1));
       coordOffsets.add(new CoordLoc(2,1,-1));
       coordOffsets.add(new CoordLoc(3,1,0));
       coordOffsets.add(new CoordLoc(-1,1,2));
       coordOffsets.add(new CoordLoc(-1,1,-2));
       coordOffsets.add(new CoordLoc(-2,1,1));
       coordOffsets.add(new CoordLoc(-2,1,-1));
       coordOffsets.add(new CoordLoc(-3,1,0));
       coordOffsets.add(new CoordLoc(0,1,3));
       coordOffsets.add(new CoordLoc(0,1,-3));
       
       coordOffsets.add(new CoordLoc(1,2,2));
       coordOffsets.add(new CoordLoc(1,2,-2));
       coordOffsets.add(new CoordLoc(2,2,1));
       coordOffsets.add(new CoordLoc(2,2,-1));
       coordOffsets.add(new CoordLoc(3,2,0));
       coordOffsets.add(new CoordLoc(-1,2,2));
       coordOffsets.add(new CoordLoc(-1,2,-2));
       coordOffsets.add(new CoordLoc(-2,2,1));
       coordOffsets.add(new CoordLoc(-2,2,-1));
       coordOffsets.add(new CoordLoc(-3,2,0));
       coordOffsets.add(new CoordLoc(0,2,3));
       coordOffsets.add(new CoordLoc(0,2,-3));
       
       coordOffsets.add(new CoordLoc(2,3,0));
       coordOffsets.add(new CoordLoc(1,3,1));
       coordOffsets.add(new CoordLoc(0,3,2));
       coordOffsets.add(new CoordLoc(-1,3,1));
       coordOffsets.add(new CoordLoc(-2,3,0));
       coordOffsets.add(new CoordLoc(1,3,-1));
       coordOffsets.add(new CoordLoc(0,3,-2));
       coordOffsets.add(new CoordLoc(-1,3,-1));
       
       coordOffsets.add(new CoordLoc(1,4,0));
       coordOffsets.add(new CoordLoc(-1,4,0));
       coordOffsets.add(new CoordLoc(0,4,1));
       coordOffsets.add(new CoordLoc(0,4,-1));
       
       coordOffsets.add(new CoordLoc(0,5,0));


	}

}
