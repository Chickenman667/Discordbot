# File Name: bot.py

import discord
from discord.ext import commands

# Create the bot with a command prefix
intents = discord.Intents.default()
intents.messages = True
bot = commands.Bot(command_prefix="!", intents=intents)

# When the bot is ready
@bot.event
async def on_ready():
    print(f"Logged in as {bot.user}")

# Ban Command
@bot.command()
@commands.has_permissions(administrator=True)
async def ban(ctx, user: discord.User, *, reason=None):
    await user.ban(reason=reason)
    await ctx.send(f"{user} has been banned!")

# Kick Command
@bot.command()
@commands.has_permissions(administrator=True)
async def kick(ctx, user: discord.User, *, reason=None):
    await user.kick(reason=reason)
    await ctx.send(f"{user} has been kicked!")

# Mute Command (using a role to mute)
@bot.command()
@commands.has_permissions(administrator=True)
async def mute(ctx, user: discord.User, *, reason=None):
    muted_role = discord.utils.get(ctx.guild.roles, name="Muted")
    if not muted_role:
        # Create "Muted" role if it doesn't exist
        muted_role = await ctx.guild.create_role(name="Muted", permissions=discord.Permissions(send_messages=False, speak=False))
    await user.add_roles(muted_role, reason=reason)
    await ctx.send(f"{user} has been muted!")

# Unmute Command
@bot.command()
@commands.has_permissions(administrator=True)
async def unmute(ctx, user: discord.User):
    muted_role = discord.utils.get(ctx.guild.roles, name="Muted")
    if muted_role:
        await user.remove_roles(muted_role)
        await ctx.send(f"{user} has been unmuted!")

# Error handling for missing permissions
@ban.error
@kick.error
@mute.error
@unmute.error
async def command_error(ctx, error):
    if isinstance(error, commands.MissingPermissions):
        await ctx.send("You don't have permission to execute this command!")

# Run the bot
bot.run('MTM0ODMzNTc2MDk5NDA3NDYyNA.GRJNni.zIleh1UWgJzJFuWtMsfZ10CeNSDOaWQaO8VLp4')
