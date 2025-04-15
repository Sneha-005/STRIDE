import os
import subprocess
import shutil
import re
import logging

logging.basicConfig(level=logging.INFO, format="%(message)s")

def run_command(command, exit_on_error=True):
    result = subprocess.run(command, shell=True, capture_output=True, text=True)
    if result.returncode != 0:
        logging.error(f"❌ Error: {result.stderr.strip()}")
        if exit_on_error:
            exit(1)
    return result.stdout.strip()

def ensure_git_filter_repo():
    logging.info("🔍 Checking for git-filter-repo...")
    
    if shutil.which("git-filter-repo") is None:
        logging.info("❌ git-filter-repo not found! Installing using pacman...")
        run_command("sudo pacman -S --noconfirm git-filter-repo")

def validate_repo_url(url):
    if not (url.startswith("http://") or url.startswith("https://") or url.startswith("git@")):
        logging.error(f"❌ Invalid repository URL: {url}")
        exit(1)

def extract_repo_name(url):
    match = re.search(r"/([^/]+?)(\.git)?$", url)
    if match:
        return match.group(1).replace(".git", "")
    logging.error(f"❌ Could not extract repository name from URL: {url}")
    exit(1)

def transfer_repo(old_repo_url, new_repo_url, new_author_name, new_author_email):
    try:
        validate_repo_url(old_repo_url)
        validate_repo_url(new_repo_url)

        repo_name = extract_repo_name(old_repo_url)
        bare_repo = f"{repo_name}.git"

        logging.info("👤 Cloning the repository...")
        run_command(f"git clone --bare {old_repo_url}")

        os.chdir(bare_repo)

        ensure_git_filter_repo()

        logging.info("✏️ Rewriting commit authorship and messages...")
        run_command(
            f'git filter-repo --commit-callback "commit.author_name = commit.committer_name = b\'{new_author_name}\'; commit.author_email = commit.committer_email = b\'{new_author_email}\'; commit.message = commit.message.replace(b\'HarshSingh011\', b\'Sneha-005\').replace(b\'HarshSingh011\', b\'Sneha-005\')"'
        )


        logging.info("🔗 Adding new repository remote...")
        run_command(f"git remote add new-origin {new_repo_url}")

        logging.info("🚀 Pushing all branches and tags to the new repository...")
        run_command("git push --mirror new-origin")

    except Exception as e:
        logging.error(f"❌ An error occurred: {e}")
        exit(1)

    finally:
        os.chdir("..")
        shutil.rmtree(bare_repo, ignore_errors=True)
        logging.info("✅ Repository transfer complete!")

if _name_ == "_main_":
    print(r"""
       __   _   __            __   _   _   __      _      ___ 
      / _| |_ | |   |          / _| | | | | | __|    / \    |   _|
     | |  _   | |    | |    __  | |     | || | |  _|     / _ \     | |  
     | || |  | |    | |   |_| | |_  |  _  | | |_   / __ \    | |  
      \_| |_|   ||            \_| || || |_| //   \\   ||  
    """ )
    old_repo_url = "https://github.com/HarshSingh011/Workify"
    new_repo_url = "https://github.com/Sneha-005/Workify"
    new_author_name = "Sneha-005"
    new_author_email = "bansalsneha991@gmail.com"

    transfer_repo(old_repo_url, new_repo_url, new_author_name, new_author_email)