package me.corpse.ObisdianBending;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

public class ObsidianBending extends EarthAbility implements AddonAbility {
	
	private Location location;
	private Location origin;
	private Vector direction;
	private Random r;

	public ObsidianBending(Player player) {
		
		super(player);
		
		if (bPlayer.isOnCooldown(this)){
			return;
		}
		setFields();
		start();
		bPlayer.addCooldown(this);
	}
	
	private void setFields() {
		this.origin = player.getLocation().clone().add(0,1,0);
		this.location = origin.clone();
		this.direction = player.getLocation().getDirection();
	}
	
	@Override
	public void progress() {
		
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}
		
		if(origin.distance(location)>5) {
			remove();
			return;
		}
		
		location.add(direction.multiply(1));
		
		ParticleEffect.SQUID_INK.display(location, 0, 0, 0, 0, 1);
		
		if (GeneralMethods.isSolid(location.getBlock())){
			remove();
			return;
		}
		
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 1))
		
		if ((entity instanceof LivingEntity) && entity.getUniqueId() != player.getUniqueId()) {
			
			DamageHandler.damageEntity(entity, 10, this);
			remove();
			return;
			
		}
		
	}

	@Override
	public long getCooldown() {
		// TODO Auto-generated method stub
		return 20000;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Obsidian Bending";
	}

	@Override
	public boolean isHarmlessAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "Corpse";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "1.0";
	}

	@Override
	public void load() {
		ProjectKorra.log.info("Ability enabled! " + getName() + " by " + getAuthor());
		
	}

	@Override
	public void stop() {
		ProjectKorra.log.info("Ability disabled! " + getName() + " by " + getAuthor());
		super.remove();
	}
	
}
